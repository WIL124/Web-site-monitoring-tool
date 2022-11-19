package thumbtack.school.api.service;

import thumbtack.school.postgres.dto.StatisticDto;

import java.util.List;

public interface StatisticService {
    List<StatisticDto> getForIntervalGrouped(String from, String to);
}
