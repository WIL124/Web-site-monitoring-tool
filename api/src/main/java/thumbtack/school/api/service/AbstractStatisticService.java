package thumbtack.school.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.postgres.dao.CommonRepository;
import thumbtack.school.postgres.dto.StatisticDto;
import thumbtack.school.postgres.model.AbstractStatistic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@AllArgsConstructor
public abstract class AbstractStatisticService<E extends AbstractStatistic, R extends CommonRepository<E>>
        implements StatisticService {
    protected R repository;

    public List<StatisticDto> getForIntervalGrouped(String from, String to) {
        return repository.selectAllCustom(dateStringToStartOfDay(from),
                dateStringToEndOfDay(to));
    }

    private LocalDateTime dateStringToEndOfDay(String dateString) {
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy")).atStartOfDay();
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    private LocalDateTime dateStringToStartOfDay(String dateString) {
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy")).atTime(LocalTime.MAX);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }
}
