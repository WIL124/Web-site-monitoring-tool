package thumbtack.school.postgres.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.dto.StatisticDto;
import thumbtack.school.postgres.model.DayOfWeekStatistic;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DayOfWeekStatisticRepository extends CommonRepository<DayOfWeekStatistic> {
    @Override
    @Query("select new thumbtack.school.postgres.dto.StatisticDto(statistic.name, SUM(statistic.count)) " +
            "from DayOfWeekStatistic as statistic where statistic.createdAt between ?1 and ?2 group by statistic.name")
    List<StatisticDto> getStatisticGrouped(LocalDateTime from, LocalDateTime to);
}
