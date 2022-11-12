package thumbtack.school.common.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.common.dao.TimeOfDayStatisticRepository;
import thumbtack.school.common.dto.StatisticDto;
import thumbtack.school.common.model.TimeOfDayStatistic;
import thumbtack.school.common.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimeOfDayStatisticService implements StatisticService<TimeOfDayStatistic> {
    private TimeOfDayStatisticRepository repository;

    public List<StatisticDto> getAll() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(TimeOfDayStatistic::getHour, Collectors.summingLong(TimeOfDayStatistic::getCount)))
                .entrySet()
                .stream()
                .map(entry -> new StatisticDto(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());
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
