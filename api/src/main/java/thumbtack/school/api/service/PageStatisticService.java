package thumbtack.school.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.postgres.dao.PageStatisticRepository;
import thumbtack.school.api.dto.StatisticDto;
import thumbtack.school.postgres.model.PageStatistic;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PageStatisticService{
    private PageStatisticRepository repository;

    public List<StatisticDto> getAll(){
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(PageStatistic::getUrl, Collectors.summingLong(PageStatistic::getCount)))
                .entrySet()
                .stream()
                .map(entry -> new StatisticDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
