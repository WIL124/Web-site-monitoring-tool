package thumbtack.school.reporter.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import thumbtack.school.hbase.dao.HbaseDao;
import thumbtack.school.hbase.model.User;
import thumbtack.school.reporter.service.StatisticService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class ReporterServiceImpl {
    @Autowired
    private Collection<StatisticService> services;
    private UserStatisticService userStatisticService;

    //    private StatisticService<BrowserStatistic> browserStatisticService;
//    private StatisticService<DayOfWeekStatistic> dayOfWeekStatisticStatisticService;
//    private StatisticService<PageStatistic> pageStatisticStatisticService;
//    private StatisticService<CountryStatistic> countryStatisticStatisticService;
//    private StatisticService<TimeOfDayStatistic> timeOfDayStatisticStatisticService;
//    private StatisticService<UserStatistic> userStatisticStatisticService;
    private HbaseDao hbaseDao;
    private static final String TABLE_NAME = "userTracker";

    @Scheduled(cron = "${cron.string}")
    public void getReport() {
        LocalDateTime dateTime = LocalDateTime.now();
        long timestampFrom = Timestamp.valueOf(dateTime.minusHours(1)).getTime();
        long timestampTo = Timestamp.valueOf(dateTime).getTime();
        List<User> users = hbaseDao.getAllUsersWithTimeRange(TABLE_NAME, timestampFrom, timestampTo);

        services.forEach(statisticService ->
                CompletableFuture
                        .supplyAsync(() -> statisticService.getStatistic(users))
                        .thenAcceptAsync(statistics -> statisticService.saveAll(statistics)));

        CompletableFuture
                .supplyAsync(() -> userStatisticService.getStatistic(users))
                .thenAcceptAsync(statistics -> userStatisticService.saveAll(statistics));
//        CompletableFuture
//                .supplyAsync(() -> browserStatisticService.getStatistic(users))
//                .thenAcceptAsync(browserStatistics -> browserStatisticService.saveAll(browserStatistics));
//
//        CompletableFuture.supplyAsync(() ->
//                        dayOfWeekStatisticStatisticService.getStatistic(users))
//                .thenAcceptAsync(dayOfWeekStatistics ->
//                        dayOfWeekStatisticStatisticService.saveAll(dayOfWeekStatistics));
//
//        CompletableFuture
//                .supplyAsync(() -> pageStatisticStatisticService.getStatistic(users))
//                .thenAcceptAsync(pageStatistics -> pageStatisticStatisticService.saveAll(pageStatistics));
//
//        CompletableFuture
//                .supplyAsync(() -> countryStatisticStatisticService.getStatistic(users))
//                .thenAcceptAsync(countryStatistics -> countryStatisticStatisticService.saveAll(countryStatistics));
//
//        CompletableFuture
//                .supplyAsync(() -> timeOfDayStatisticStatisticService.getStatistic(users))
//                .thenAcceptAsync(timeOfDayStatistics ->
//                        timeOfDayStatisticStatisticService.saveAll(timeOfDayStatistics));
//
//        CompletableFuture
//                .supplyAsync(() -> userStatisticStatisticService.getStatistic(users))
//                .thenAcceptAsync(userStatistics ->
//                        userStatisticStatisticService.saveAll(userStatistics));
    }
}
