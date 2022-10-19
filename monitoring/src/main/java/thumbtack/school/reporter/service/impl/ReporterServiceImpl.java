package thumbtack.school.reporter.service.impl;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.reporter.model.statistic.*;
import thumbtack.school.reporter.service.GeolocationService;
import thumbtack.school.reporter.service.ReporterService;
import thumbtack.school.tracking.dao.HbaseDao;
import thumbtack.school.tracking.model.User;

import java.time.DayOfWeek;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReporterServiceImpl implements ReporterService {
    private HbaseDao hbaseDao;
    private GeolocationService geolocationService;
    private static final String TABLE_NAME = "userTracker";
    private static final String IP_ADDRESS_HEADER_NAME = "IP address";

    @Override
    public void getReport(long timestampFrom, long timestampTo) {
        List<User> users = hbaseDao.getAllUsersWithTimeRange(TABLE_NAME, 0, Long.MAX_VALUE);

        CompletableFuture<List<BrowserStatistic>> browserCf = CompletableFuture
                .supplyAsync(() -> users.stream()
                        .map(this::getUserAgentsFromUser)
                        .flatMap(List::stream)
                        .collect(Collectors.toList())
                ).thenApply(this::getBrowserStatistic);

        CompletableFuture<List<CountryStatistic>> countryCf = CompletableFuture
                .supplyAsync(() -> users.stream()
                        .map(this::getIpAddressesFromUser)
                        .flatMap(List::stream)
                        .collect(Collectors.toList())
                ).thenApply(this::getCountryStatistic);

        CompletableFuture<List<DayOfWeekStatistic>> dayOfWeekCf = CompletableFuture
                .supplyAsync(() -> users.stream()
                        .map(user -> user.getTimestampHeadersMap().keySet())
                        .map(ArrayList::new)
                        .flatMap(List::stream)
                        .collect(Collectors.toList())
                ).thenApply(this::getDaysOfWeekFromTimestamps);

        CompletableFuture<List<TimeOfDayStatistic>> timeOfDayCf = CompletableFuture
                .supplyAsync(() -> users.stream()
                        .map(user -> user.getTimestampHeadersMap().keySet())
                        .map(ArrayList::new)
                        .flatMap(List::stream)
                        .collect(Collectors.toList()))
                .thenApply(this::getTimeOfDayFromTimestamps);

        CompletableFuture<List<PageStatistic>> pageCf = CompletableFuture
                .supplyAsync(() -> users.stream()
                        .map(user -> user.getTimestampHeadersMap().values().stream()
                                .map(headers -> headers.getFirst("referer"))
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList()))
                        .flatMap(List::stream)
                        .collect(Collectors.toList()))
                .thenApply(this::collectPagesFromList);

        try {
            browserCf.get();
            countryCf.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private List<PageStatistic> collectPagesFromList(List<String> strList) {
        Map<String, Long> map = new HashMap<>();
        for (String str : strList) {
            map.merge(str, 1L, Long::sum);
        }
        return map.entrySet().stream()
                .map(entry -> new PageStatistic(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private List<TimeOfDayStatistic> getTimeOfDayFromTimestamps(List<Long> tsList) {
        Map<Integer, Long> map = new HashMap<>();
        Calendar calendar = GregorianCalendar.getInstance();
        for (Long ts : tsList) {
            calendar.setTimeInMillis(ts);
            map.merge(calendar.get(Calendar.HOUR_OF_DAY), 1L, Long::sum);
        }
        return map.entrySet().stream()
                .map(entry -> new TimeOfDayStatistic(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private List<DayOfWeekStatistic> getDaysOfWeekFromTimestamps(List<Long> tsList) {
        Map<DayOfWeek, Long> map = new HashMap<>();
        Calendar calendar = GregorianCalendar.getInstance();
        for (Long ts : tsList) {
            calendar.setTimeInMillis(ts);
            map.merge(DayOfWeek.of(calendar.get(Calendar.DAY_OF_WEEK)), 1L, Long::sum);
        }
        return map.entrySet().stream()
                .map(entry -> new DayOfWeekStatistic(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private List<UserAgent> getUserAgentsFromUser(User user) {
        return user.getTimestampHeadersMap().values().stream()
                .map(headers -> headers.getFirst("user-agent"))
                .map(UserAgent::parseUserAgentString).collect(Collectors.toList());
    }

    private List<String> getIpAddressesFromUser(User user) {
        return user.getTimestampHeadersMap().values().stream()
                .map(headers -> headers.getFirst(IP_ADDRESS_HEADER_NAME))
                .collect(Collectors.toList());
    }

    private List<BrowserStatistic> getBrowserStatistic(List<UserAgent> userAgentList) {
        Map<String, Long> map = new HashMap<>();
        for (UserAgent userAgent : userAgentList) {
            map.merge(userAgent.getBrowser().getName(), 1L, Long::sum);
        }
        return map.entrySet().stream()
                .map(entry -> new BrowserStatistic(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private List<CountryStatistic> getCountryStatistic(List<String> ipAddressList) {
        Map<String, Long> map = new HashMap<>();
        for (String ipAddress : ipAddressList) {
            map.merge(geolocationService.getCountryFromIP(ipAddress), 1L, Long::sum);
        }
        return map.entrySet().stream()
                .map(entry -> new CountryStatistic(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }


    ////////////////////////deprecated too////////////////////
//    private List<SessionReport> createUserReports(User user) {
//        List<SessionReport> reports = new ArrayList<>();
//        for (Map.Entry<Long, HttpHeaders> timestampHeadersEntry : user.getTimestampHeadersMap().entrySet()) {
//            HttpHeaders headers = timestampHeadersEntry.getValue();
//            String language = headers.getFirst(HttpHeaders.ACCEPT_LANGUAGE);
//            String region = geolocationService.getCountryFromIP(headers.getFirst(IP_ADDRESS_HEADER_NAME));
//            String os = null, platform = null;
//            if (headers.getFirst(HttpHeaders.USER_AGENT) != null) {
//                UserAgent userAgent = UserAgent.parseUserAgentString(headers.getFirst(HttpHeaders.USER_AGENT));
//                os = userAgent.getOperatingSystem().getName();
//                platform = userAgent.getOperatingSystem().getDeviceType().getName();
//            }
//            long ts = timestampHeadersEntry.getKey();
//            reports.add(SessionReport.builder().timestamp(ts).language(language).region(region).os(os).platform(platform).user(user).build());
//        }
//        return reports;
//    }

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
