package thumbtack.school.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.api.dto.StatisticDto;
import thumbtack.school.reporter.dao.CountryStatisticRepository;
import thumbtack.school.reporter.model.CountryStatistic;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CountryStatisticService {
    private CountryStatisticRepository repository;
    public List<StatisticDto> getAll() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(CountryStatistic::getName, Collectors.summingLong(CountryStatistic::getCount)))
                .entrySet()
                .stream()
                .map(entry -> new StatisticDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
