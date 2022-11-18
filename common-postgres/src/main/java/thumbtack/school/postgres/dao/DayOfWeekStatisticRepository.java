package thumbtack.school.postgres.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.model.DayOfWeekStatistic;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DayOfWeekStatisticRepository extends CommonRepository<DayOfWeekStatistic> {
    List<DayOfWeekStatistic> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT new DayOfWeekStatistic(b.name, SUM(b.count)) " +
            "FROM DayOfWeekStatistic as b " +
            "WHERE b.createdAt >= :#{#fromDate} " +
            "AND b.createdAt <= :#{#toDate} GROUP BY b.name")
    List<DayOfWeekStatistic> findByCreatedAtBetweenAndGroupedByName(@Param("fromDate") LocalDateTime fromDate,
                                                                    @Param("toDate") LocalDateTime to);

    @Query("SELECT new DayOfWeekStatistic(b.name, sum(b.count)) " +
            "FROM DayOfWeekStatistic as b GROUP BY b.name")
    List<DayOfWeekStatistic> selectAllGroupedByName();
}
