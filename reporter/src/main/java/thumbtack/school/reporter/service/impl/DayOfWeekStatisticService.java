package thumbtack.school.reporter.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.common.model.User;
import thumbtack.school.reporter.dao.DayOfWeekStatisticRepository;
import thumbtack.school.reporter.model.DayOfWeekStatistic;
import thumbtack.school.reporter.service.StatisticService;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DayOfWeekStatisticService implements StatisticService<DayOfWeekStatistic> {
    private DayOfWeekStatisticRepository repository;

    @Override
    public List<DayOfWeekStatistic> getStatistic(List<User> users) {
        List<Long> timestamps = users.stream()
                .map(user -> user.getTimestampHeadersMap().keySet())
                .map(ArrayList::new)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return getDaysOfWeekFromTimestamps(timestamps);
    }

    @Override
    public void saveAll(List<DayOfWeekStatistic> statistic) {
        repository.saveAll(statistic);
    }

    private List<DayOfWeekStatistic> getDaysOfWeekFromTimestamps(List<Long> tsList) {
        Map<DayOfWeek, Long> map = new HashMap<>();
        Calendar calendar = GregorianCalendar.getInstance();
        for (Long ts : tsList) {
            calendar.setTimeInMillis(ts);
            map.merge(DayOfWeek.of(calendar.get(Calendar.DAY_OF_WEEK) - 1), 1L, Long::sum);
        }
        return map.entrySet().stream()
                .map(entry ->
                        new DayOfWeekStatistic(entry.getKey().getDisplayName(TextStyle.FULL, Locale.ENGLISH), entry.getValue()))
                .collect(Collectors.toList());
    }
}
