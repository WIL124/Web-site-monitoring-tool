package thumbtack.school.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.postgres.dao.CommonRepository;
import thumbtack.school.postgres.dto.StatisticDto;
import thumbtack.school.postgres.model.AbstractStatistic;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public abstract class AbstractStatisticService<E extends AbstractStatistic, R extends CommonRepository<E>>
        implements StatisticService {
    protected R repository;

    public List<StatisticDto> getAllGroupedByNameAndCreatedAtBetween(LocalDate from, LocalDate to) {
        from = (from == null) ? LocalDate.EPOCH : from;
        to = (to == null) ? LocalDate.of(2100, 1, 1) : to;
        return repository.getStatisticGrouped(from.atStartOfDay(), to.atTime(LocalTime.MAX));
    }
}
