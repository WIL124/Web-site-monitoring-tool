package thumbtack.school.reporter.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.reporter.model.*;
import thumbtack.school.reporter.service.ReporterService;
import thumbtack.school.reporter.service.StatisticService;
import thumbtack.school.tracking.dao.HbaseDao;
import thumbtack.school.tracking.model.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class ReporterServiceImpl implements ReporterService {

    private StatisticService<BrowserStatistic> browserStatisticService;
    private StatisticService<DayOfWeekStatistic> dayOfWeekStatisticStatisticService;
    private StatisticService<PageStatistic> pageStatisticStatisticService;
    private StatisticService<CountryStatistic> countryStatisticStatisticService;
    private StatisticService<TimeOfDayStatistic> timeOfDayStatisticStatisticService;
    private HbaseDao hbaseDao;
    private static final String TABLE_NAME = "userTracker";

    @Override
    public void getReport(long timestampFrom, long timestampTo) {
        List<User> users = hbaseDao.getAllUsersWithTimeRange(TABLE_NAME, 0, Long.MAX_VALUE);

        CompletableFuture
                .supplyAsync(() -> browserStatisticService.getStatistic(users))
                .thenAcceptAsync(browserStatistics -> browserStatisticService.saveAll(browserStatistics));

        CompletableFuture.supplyAsync(() ->
                        dayOfWeekStatisticStatisticService.getStatistic(users))
                .thenAcceptAsync(dayOfWeekStatistics ->
                        dayOfWeekStatisticStatisticService.saveAll(dayOfWeekStatistics));

        CompletableFuture
                .supplyAsync(() -> pageStatisticStatisticService.getStatistic(users))
                .thenAcceptAsync(pageStatistics -> pageStatisticStatisticService.saveAll(pageStatistics));

        CompletableFuture
                .supplyAsync(() -> countryStatisticStatisticService.getStatistic(users))
                .thenAcceptAsync(pageStatistics -> countryStatisticStatisticService.saveAll(pageStatistics));

        CompletableFuture
                .supplyAsync(() -> timeOfDayStatisticStatisticService.getStatistic(users))
                .thenAcceptAsync(timeOfDayStatistics ->
                        timeOfDayStatisticStatisticService.saveAll(timeOfDayStatistics));
    }
}
