package thumbtack.school.api.endpoint;

import thumbtack.school.postgres.dto.StatisticDto;

import java.util.List;

public interface CommonController {
    List<StatisticDto> getAll(String from, String to);
}
