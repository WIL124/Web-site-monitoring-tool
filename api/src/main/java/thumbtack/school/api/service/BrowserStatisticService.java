package thumbtack.school.api.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import thumbtack.school.postgres.dao.BrowserStatisticRepository;
import thumbtack.school.api.dto.StatisticDto;
import thumbtack.school.postgres.model.BrowserStatistic;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BrowserStatisticService{
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
