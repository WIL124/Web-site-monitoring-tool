package thumbtack.school.reporter.service.impl;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import thumbtack.school.reporter.model.SessionReport;
import thumbtack.school.reporter.model.statistic.BrowserStatistic;
import thumbtack.school.reporter.model.statistic.CountryStatistic;
import thumbtack.school.reporter.service.GeolocationService;
import thumbtack.school.reporter.service.ReporterService;
import thumbtack.school.tracking.dao.HbaseDao;
import thumbtack.school.tracking.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReporterServiceImpl implements ReporterService {
    private HbaseDao hbaseDao;
    private GeolocationService geolocationService;
    private static final String TABLE_NAME = "userTracker";
    private static final String IP_ADDRESS_HEADER_NAME = "IP address";

    @Override
    public List<SessionReport> getReport(long timestampFrom, long timestampTo) {
        List<User> users = hbaseDao.getAllUsersWithTimeRange(TABLE_NAME, 0, Long.MAX_VALUE);

        CompletableFuture<BrowserStatistic> browserStatisticCf = CompletableFuture
                .supplyAsync(() -> users.stream()
                        .map(this::getUserAgentsFromUser)
                        .flatMap(List::stream)
                        .collect(Collectors.toList())
                ).thenApply(this::getBrowserStatistic);

        CompletableFuture<CountryStatistic> countryStatisticCf = CompletableFuture
                .supplyAsync(() -> users.stream()
                        .map(this::getIpAddressesFromUser)
                        .flatMap(List::stream)
                        .collect(Collectors.toList())
                ).thenApply(this::getCountryStatistic);

        return users.stream()
                .map(this::createUserReports)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<UserAgent> getUserAgentsFromUser(User user) {
        return user.getTimestampHeadersMap().values().stream()
                .map(headers -> headers.getFirst("user-agent"))
                .map(UserAgent::parseUserAgentString).collect(Collectors.toList());
    }
    private List<String> getIpAddressesFromUser(User user){
        return user.getTimestampHeadersMap().values().stream()
                .map(headers -> headers.getFirst(IP_ADDRESS_HEADER_NAME))
                .collect(Collectors.toList());
    }

    private BrowserStatistic getBrowserStatistic(List<UserAgent> userAgentList) {
        Map<String, Integer> map = new HashMap<>();
        for (UserAgent userAgent : userAgentList) {
            map.merge(userAgent.getBrowser().getName(), 1, Integer::sum);
        }
        return new BrowserStatistic(map);
    }

    private CountryStatistic getCountryStatistic(List<String> ipAddressList) {
        Map<String, Integer> map = new HashMap<>();
        for (String ipAddress : ipAddressList) {
            map.merge(geolocationService.getCountryFromIP(ipAddress), 1, Integer::sum);
        }
        return new CountryStatistic(map);
    }


    ////////////////////////deprecated too////////////////////
    private List<SessionReport> createUserReports(User user) {
        List<SessionReport> reports = new ArrayList<>();
        for (Map.Entry<Long, HttpHeaders> timestampHeadersEntry : user.getTimestampHeadersMap().entrySet()) {
            HttpHeaders headers = timestampHeadersEntry.getValue();
            String language = headers.getFirst(HttpHeaders.ACCEPT_LANGUAGE);
            String region = geolocationService.getCountryFromIP(headers.getFirst(IP_ADDRESS_HEADER_NAME));
            String os = null, platform = null;
            if (headers.getFirst(HttpHeaders.USER_AGENT) != null) {
                UserAgent userAgent = UserAgent.parseUserAgentString(headers.getFirst(HttpHeaders.USER_AGENT));
                os = userAgent.getOperatingSystem().getName();
                platform = userAgent.getOperatingSystem().getDeviceType().getName();
            }
            long ts = timestampHeadersEntry.getKey();
            reports.add(SessionReport.builder().timestamp(ts).language(language).region(region).os(os).platform(platform).user(user).build());
        }
        return reports;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////DEPRECATED//////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    private SessionReport createUserReports(List<User> users) {
//        SessionReport sessionReport = new SessionReport();
//        sessionReport.setUsers(users.size());
//        sessionReport.setVisits(users.stream().mapToLong(user -> user.getTimestampHeadersMap().size()).sum());
//        for (User user : users) {
//            Map<Long, HttpHeaders> timestampHeadersMap = user.getTimestampHeadersMap();
//            for (HttpHeaders httpHeaders : timestampHeadersMap.values()) {
//                accumulateLanguageData(httpHeaders, sessionReport.getLanguageMap());
//                accumulateRegionData(httpHeaders, sessionReport.getRegionMap());
//                Optional<String> userAgentString = Optional.ofNullable(httpHeaders.getFirst("User-Agent"));
//                if (userAgentString.isPresent()) {
//                    UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString.get());
//                    accumulateBrowserData(userAgent, sessionReport.getBrowserMap());
//                    accumulateOsData(userAgent, sessionReport.getOsMap());
//                    accumulatePlatformData(userAgent, sessionReport.getPlatformMap());
//                }
//            }
//        }
//        return sessionReport;
//    }
//
//    private void accumulatePlatformData(UserAgent userAgent, Map<String, Long> platformMap) {
//        DeviceType deviceType = userAgent.getOperatingSystem().getDeviceType();
//        platformMap.merge(deviceType.getName(), 1L, Long::sum);
//    }
//
//    private void accumulateBrowserData(UserAgent userAgent, Map<String, Long> browserMap) {
//        Browser browser = userAgent.getBrowser();
//        while (!browser.equals(browser.getGroup())) {  //get largest Browser group
//            browser = browser.getGroup();
//        }
//        browserMap.merge(browser.getName(), 1L, Long::sum);
//    }
//
//    private void accumulateOsData(UserAgent userAgent, Map<String, Long> osMap) {
//        OperatingSystem os = userAgent.getOperatingSystem();
//        while (!os.equals(os.getGroup())) {  //get largest OS group
//            os = os.getGroup();
//        }
//        osMap.merge(os.getName(), 1L, Long::sum);
//    }
//
//    private void accumulateLanguageData(HttpHeaders headers, Map<String, Long> languageMap) {
//        if (headers.getAcceptLanguage().isEmpty()) return;
//        Optional<Locale> optionalLocale = headers.getAcceptLanguageAsLocales().stream().findFirst();
//        optionalLocale.ifPresent(locale -> languageMap.merge(locale.getLanguage(), 1L, Long::sum));
//    }
//
//    private void accumulateRegionData(HttpHeaders headers, Map<String, Long> countryMap) {
//        if (headers.getOrEmpty("ipAddress").isEmpty()) return;
//        String country = geolocationService.getCountryFromIP(headers.getFirst("ipAddress"));
//        countryMap.merge(country, 1L, Long::sum);
//    }

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
