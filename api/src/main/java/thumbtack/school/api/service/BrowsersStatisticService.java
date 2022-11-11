package thumbtack.school.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.api.dto.StatisticDto;
import thumbtack.school.reporter.dao.BrowserStatisticRepository;
import thumbtack.school.reporter.model.BrowserStatistic;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BrowsersStatisticService {
    private BrowserStatisticRepository repository;
    public List<StatisticDto> getAll() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(BrowserStatistic::getName, Collectors.summingLong(BrowserStatistic::getCount)))
                .entrySet()
                .stream()
                .map(entry -> new StatisticDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
