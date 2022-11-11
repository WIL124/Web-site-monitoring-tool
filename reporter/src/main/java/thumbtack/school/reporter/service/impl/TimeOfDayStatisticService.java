package thumbtack.school.reporter.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.reporter.dao.TimeOfDayStatisticRepository;
import thumbtack.school.reporter.model.TimeOfDayStatistic;
import thumbtack.school.reporter.service.StatisticService;
import thumbtack.school.common.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimeOfDayStatisticService implements StatisticService<TimeOfDayStatistic> {
    private TimeOfDayStatisticRepository repository;

    @Override
    public List<TimeOfDayStatistic> getStatistic(List<User> users) {
        List<Long> timestamps = users.stream()
                .map(user -> user.getTimestampHeadersMap().keySet())
                .map(ArrayList::new)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return getTimeOfDayFromTimestamps(timestamps);
    }

    @Override
    public void saveAll(List<TimeOfDayStatistic> statistic) {
        repository.saveAll(statistic);
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
}
