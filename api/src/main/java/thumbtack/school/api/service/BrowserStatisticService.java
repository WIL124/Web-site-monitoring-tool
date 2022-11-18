package thumbtack.school.api.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import thumbtack.school.api.dto.IntervalRequest;
import thumbtack.school.api.dto.StatisticDto;
import thumbtack.school.api.mapper.StatisticMapper;
import thumbtack.school.postgres.dao.BrowserStatisticRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BrowserStatisticService implements StatisticService {
    private BrowserStatisticRepository repository;
    private StatisticMapper mapper;

    @Override
    public List<StatisticDto> getAllRaw() {
        return repository.findAll().stream()
                .map(browserStatistic ->
                        mapper.toDto(browserStatistic))
                .collect(Collectors.toList());
    }

    public List<StatisticDto> getAllGrouped() {
        return repository.selectAllGroupedByName().stream()
                .map(browserStatistic ->
                        mapper.toDto(browserStatistic))
                .collect(Collectors.toList());
    }

    @Override
    public List<StatisticDto> getForInterval(IntervalRequest intervalRequest) {
        return repository.findByCreatedAtBetween(intervalRequest.getFrom().atStartOfDay(),
                        intervalRequest.getTo().atTime(LocalTime.MAX)).stream()
                .map(s -> mapper.toDto(s))
                .collect(Collectors.toList());
    }

    public List<StatisticDto> getForIntervalGrouped(IntervalRequest intervalRequest) {
        return repository.findByCreatedAtBetweenAndGroupedByName(intervalRequest.getFrom().atStartOfDay(),
                        intervalRequest.getTo().atTime(LocalTime.MAX)).stream()
                .map(s -> mapper.toDto(s))
                .collect(Collectors.toList());
    }
}
