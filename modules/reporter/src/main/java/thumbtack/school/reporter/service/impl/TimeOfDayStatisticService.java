package thumbtack.school.reporter.service.impl;

import org.springframework.stereotype.Service;
import thumbtack.school.hbase.model.User;
import thumbtack.school.postgres.dao.TimeOfDayStatisticRepository;
import thumbtack.school.postgres.model.TimeOfDayStatistic;
import thumbtack.school.reporter.service.AbstractStatisticService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimeOfDayStatisticService extends AbstractStatisticService<TimeOfDayStatistic, TimeOfDayStatisticRepository> {
    public TimeOfDayStatisticService(TimeOfDayStatisticRepository repository) {
        super(repository);
    }

    @Override
    public List<TimeOfDayStatistic> getStatistic(List<User> users) {
        List<Long> timestamps = users.stream()
                .map(user -> user.getTimestampHeadersMap().keySet())
                .map(ArrayList::new)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return getTimeOfDayFromTimestamps(timestamps);
    }

    private List<TimeOfDayStatistic> getTimeOfDayFromTimestamps(List<Long> tsList) {
        Map<Integer, Long> map = new HashMap<>();
        Calendar calendar = GregorianCalendar.getInstance();
        for (Long ts : tsList) {
            calendar.setTimeInMillis(ts);
            map.merge(calendar.get(Calendar.HOUR_OF_DAY), 1L, Long::sum);
        }
        return map.entrySet().stream()
                .map(entry -> new TimeOfDayStatistic(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
