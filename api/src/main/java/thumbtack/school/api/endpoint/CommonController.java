package thumbtack.school.api.endpoint;

import thumbtack.school.api.dto.IntervalRequest;
import thumbtack.school.api.dto.StatisticDto;

import java.util.List;

public interface CommonController {
    List<StatisticDto> getAll();

    List<StatisticDto> getAllRaw();

    List<StatisticDto> getForInterval(IntervalRequest intervalRequest);

    List<StatisticDto> getForIntervalGrouped(IntervalRequest intervalRequest);
}
