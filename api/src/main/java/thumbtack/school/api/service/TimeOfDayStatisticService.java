package thumbtack.school.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.postgres.dao.TimeOfDayStatisticRepository;
import thumbtack.school.api.dto.StatisticDto;
import thumbtack.school.postgres.model.TimeOfDayStatistic;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimeOfDayStatisticService{
    private TimeOfDayStatisticRepository repository;

    public List<StatisticDto> getAll() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(TimeOfDayStatistic::getHour, Collectors.summingLong(TimeOfDayStatistic::getCount)))
                .entrySet()
                .stream()
                .map(entry -> new StatisticDto(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
