package thumbtack.school.reporter.service;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import thumbtack.school.reporter.model.Report;
import thumbtack.school.tracking.dao.HbaseDao;
import thumbtack.school.tracking.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReporterServiceImpl implements ReporterService {
    private static final String TABLE_NAME = "userTracker";
    @Autowired
    private HbaseDao hbaseDao;


    @Override
    public Report getReport(LocalDateTime dateTime) { //TODO need dateTime filter (on hbase scan)
        List<User> users = hbaseDao.getAllUsers(TABLE_NAME);
        //        Future<Map<String, Long>> browserMap = executor.submit(() -> getLanguageMap(users));
        Report report = createReport(users);
        return report;
    }

    private Report createReport(List<User> users) {
        Report report = new Report();
        report.setUsers(users.size());
        report.setVisits(users.stream().mapToLong(user -> user.getTimestampHeadersMap().size()).sum());
        for (User user : users) {
            Map<Long, HttpHeaders> timestampHeadersMap = user.getTimestampHeadersMap();
            for (HttpHeaders httpHeaders : timestampHeadersMap.values()) {
                accumulateLanguageData(httpHeaders, report.getLanguageMap());
                Optional<String> userAgentString = Optional.ofNullable(httpHeaders.getFirst("User-Agent"));
                if (userAgentString.isPresent()) {
                    UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString.get());
                    accumulateBrowserData(userAgent, report.getBrowserMap());
                    accumulateOsData(userAgent, report.getOsMap());
                    accumulatePlatformData(userAgent, report.getPlatformMap());
                }
            }
        }
        return report;
    }

    private void accumulatePlatformData(UserAgent userAgent, Map<String, Long> platformMap) {
        DeviceType deviceType = userAgent.getOperatingSystem().getDeviceType();
        platformMap.merge(deviceType.getName(), 1L, Long::sum);
    }

    private void accumulateBrowserData(UserAgent userAgent, Map<String, Long> browserMap) {
        Browser browser = userAgent.getBrowser();
        while (!browser.equals(browser.getGroup())) {  //get largest Browser group
            browser = browser.getGroup();
        }
        browserMap.merge(browser.getName(), 1L, Long::sum);
    }

    private void accumulateOsData(UserAgent userAgent, Map<String, Long> osMap) {
        OperatingSystem os = userAgent.getOperatingSystem();
        while (!os.equals(os.getGroup())) {  //get largest OS group
            os = os.getGroup();
        }
        osMap.merge(os.getName(), 1L, Long::sum);
    }

    private void accumulateLanguageData(HttpHeaders headers, Map<String, Long> languageMap) {
        if (headers.getAcceptLanguage().isEmpty()) return;
        Optional<Locale> optionalLocale = headers.getAcceptLanguageAsLocales().stream().findFirst();
        optionalLocale.ifPresent(locale -> languageMap.merge(locale.getLanguage(), 1L, Long::sum));
    }

//    private Map<String, Long> getLanguageMap(List<User> users) {
//        Map<String, Long> map = new HashMap<>();
//        for (User user : users) {
//            for (HttpHeaders headers : user.getTimestampHeadersMap().values()) {
//                if (headers.getAcceptLanguage().isEmpty()) continue;
//                Optional<Locale> optionalLocale = headers.getAcceptLanguageAsLocales().stream().findFirst();
//                optionalLocale.ifPresent(locale -> map.merge(locale.getLanguage(), 1L, Long::sum));
//            }
//        }
//        return map;
//    }
//
//    private Map<String, Long> getOsMap(List<User> users) {
//        Map<String, Long> map = new HashMap<>();
//        for (User user : users) {
//            for (HttpHeaders headers : user.getTimestampHeadersMap().values()) {
//                Optional<String> userAgentString = Optional.ofNullable(headers.getFirst("User-Agent"));
//                if (!userAgentString.isPresent()) {
//                    continue;
//                }
//                UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString.get());
//                OperatingSystem os = userAgent.getOperatingSystem();
//                while (!os.equals(os.getGroup())){  //get largest OS group
//                    os = os.getGroup();
//                }
//                map.merge(os.getName(), 1L, Long::sum);
//            }
//        }
//        return map;
//    }
//    private Map<String, Long> getBrowserMap(List<User> users){
//        Map<String, Long> map = new HashMap<>();
//        for (User user : users) {
//            for (HttpHeaders headers : user.getTimestampHeadersMap().values()) {
//                Optional<String> userAgentString = Optional.ofNullable(headers.getFirst("User-Agent"));
//                if (!userAgentString.isPresent()) {
//                    continue;
//                }
//                UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString.get());
//                Browser browser = userAgent.getBrowser();
//                while (!browser.equals(browser.getGroup())){  //get largest Browser group
//                    browser = browser.getGroup();
//                }
//                map.merge(browser.getName(), 1L, Long::sum);
//            }
//        }
//        return map;
//    }
}
