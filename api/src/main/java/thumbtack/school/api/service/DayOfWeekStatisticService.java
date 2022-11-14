package thumbtack.school.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.postgres.dao.DayOfWeekStatisticRepository;
import thumbtack.school.api.dto.StatisticDto;
import thumbtack.school.postgres.model.DayOfWeekStatistic;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DayOfWeekStatisticService {
    private DayOfWeekStatisticRepository repository;

    public List<StatisticDto> getAll() {
        return repository.findAll()
                .stream()
                .collect(Collectors.groupingBy(DayOfWeekStatistic::getDayOfWeek, Collectors.summingLong(DayOfWeekStatistic::getCount)))
                .entrySet()
                .stream()
                .map(entry -> new StatisticDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
