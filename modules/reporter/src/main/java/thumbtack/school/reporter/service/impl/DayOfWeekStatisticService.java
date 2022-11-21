package thumbtack.school.reporter.service.impl;

import org.springframework.stereotype.Service;
import thumbtack.school.hbase.model.User;
import thumbtack.school.postgres.dao.DayOfWeekStatisticRepository;
import thumbtack.school.postgres.model.DayOfWeekStatistic;
import thumbtack.school.reporter.service.AbstractStatisticService;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DayOfWeekStatisticService extends AbstractStatisticService<DayOfWeekStatistic, DayOfWeekStatisticRepository> {
    public DayOfWeekStatisticService(DayOfWeekStatisticRepository repository) {
        super(repository);
    }

    @Override
    public List<DayOfWeekStatistic> getStatistic(List<User> users) {
        List<Long> timestamps = users.stream()
                .map(user -> user.getTimestampHeadersMap().keySet())
                .map(ArrayList::new)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return getDaysOfWeekFromTimestamps(timestamps);
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
