package thumbtack.school.api.endpoint;

import thumbtack.school.postgres.dto.StatisticDto;

import java.time.LocalDate;
import java.util.List;

public interface CommonController {
    List<StatisticDto> getAll(LocalDate from, LocalDate to);
}
