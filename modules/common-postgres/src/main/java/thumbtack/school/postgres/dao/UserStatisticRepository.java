package thumbtack.school.postgres.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.dto.StatisticDto;
import thumbtack.school.postgres.model.UserStatistic;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserStatisticRepository extends CommonRepository<UserStatistic> {
    @Override
    @Query("select new thumbtack.school.postgres.dto.StatisticDto(statistic.name, statistic.count, statistic.createdAt) " +
            "from UserStatistic as statistic where statistic.createdAt between ?1 and ?2")
    List<StatisticDto> getStatisticGrouped(LocalDateTime from, LocalDateTime to);
}
