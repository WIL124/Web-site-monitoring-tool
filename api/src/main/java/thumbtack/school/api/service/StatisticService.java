package thumbtack.school.api.service;

import thumbtack.school.api.dto.IntervalRequest;
import thumbtack.school.api.dto.StatisticDto;

import java.util.List;

public interface StatisticService {
    /**
     * all rows from db
     **/
    List<StatisticDto> getAllRaw();

    /**
     * all rows from db grouped by name
     **/
    List<StatisticDto> getAllGrouped();

    /**
     * all rows from db grouped by name for interval
     **/
    List<StatisticDto> getForInterval(IntervalRequest intervalRequest);
}
