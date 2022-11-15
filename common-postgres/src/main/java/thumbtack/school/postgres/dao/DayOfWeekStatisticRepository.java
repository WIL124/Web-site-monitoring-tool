package thumbtack.school.postgres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.model.BrowserStatistic;
import thumbtack.school.postgres.model.DayOfWeekStatistic;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DayOfWeekStatisticRepository extends JpaRepository<DayOfWeekStatistic, Long> {
    List<DayOfWeekStatistic> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT new DayOfWeekStatistic(b.dayOfWeek, SUM(b.count)) " +
            "FROM DayOfWeekStatistic as b " +
            "WHERE b.createdAt >= :#{#fromDate} " +
            "AND b.createdAt <= :#{#toDate} GROUP BY b.dayOfWeek")
    List<DayOfWeekStatistic> findByCreatedAtBetweenAndGroupedByName(@Param("fromDate") LocalDateTime fromDate,
                                                                  @Param("toDate") LocalDateTime to);

    @Query("SELECT new DayOfWeekStatistic(b.dayOfWeek, sum(b.count)) " +
            "FROM DayOfWeekStatistic as b GROUP BY b.dayOfWeek")
    List<DayOfWeekStatistic> selectAllGroupedByName();
}
