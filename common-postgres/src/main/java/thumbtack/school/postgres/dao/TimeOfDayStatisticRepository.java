package thumbtack.school.postgres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.model.BrowserStatistic;
import thumbtack.school.postgres.model.TimeOfDayStatistic;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeOfDayStatisticRepository extends JpaRepository<TimeOfDayStatistic, Long> {
    List<TimeOfDayStatistic> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT new TimeOfDayStatistic(b.hour, SUM(b.count)) " +
            "FROM TimeOfDayStatistic as b " +
            "WHERE b.createdAt >= :#{#fromDate} " +
            "AND b.createdAt <= :#{#toDate} GROUP BY b.hour")
    List<TimeOfDayStatistic> findByCreatedAtBetweenAndGroupedByName(@Param("fromDate") LocalDateTime fromDate,
                                                                  @Param("toDate") LocalDateTime to);
    @Query("SELECT new TimeOfDayStatistic(b.hour, sum(b.count)) " +
            "FROM TimeOfDayStatistic as b GROUP BY b.hour")
    List<TimeOfDayStatistic> selectAllGroupedByName();
}
