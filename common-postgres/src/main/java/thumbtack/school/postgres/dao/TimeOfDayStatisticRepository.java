package thumbtack.school.postgres.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.model.TimeOfDayStatistic;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeOfDayStatisticRepository extends CommonRepository<TimeOfDayStatistic> {
    List<TimeOfDayStatistic> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT new TimeOfDayStatistic(b.name, SUM(b.count)) " +
            "FROM TimeOfDayStatistic as b " +
            "WHERE b.createdAt >= :#{#fromDate} " +
            "AND b.createdAt <= :#{#toDate} GROUP BY b.name")
    List<TimeOfDayStatistic> findByCreatedAtBetweenAndGroupedByName(@Param("fromDate") LocalDateTime fromDate,
                                                                  @Param("toDate") LocalDateTime to);
    @Query("SELECT new TimeOfDayStatistic(b.name, sum(b.count)) " +
            "FROM TimeOfDayStatistic as b GROUP BY b.name")
    List<TimeOfDayStatistic> selectAllGroupedByName();
}
