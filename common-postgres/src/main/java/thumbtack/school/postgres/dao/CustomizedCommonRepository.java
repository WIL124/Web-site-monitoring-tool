package thumbtack.school.postgres.dao;

import thumbtack.school.postgres.dto.StatisticDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomizedCommonRepository {
    List<StatisticDto> selectAllCustom(LocalDateTime fromDate, LocalDateTime to);
}
