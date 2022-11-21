package thumbtack.school.api.service;

import thumbtack.school.postgres.dto.StatisticDto;

import java.time.LocalDate;
import java.util.List;

public interface StatisticService {
    List<StatisticDto> getAllGroupedByNameAndCreatedAtBetween(LocalDate from, LocalDate to);
}
