package thumbtack.school.postgres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.model.BrowserStatistic;
import thumbtack.school.postgres.model.DayOfWeekStatistic;
import thumbtack.school.postgres.model.PageStatistic;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PageStatisticRepository extends JpaRepository<PageStatistic, Long> {
    List<PageStatistic> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT new PageStatistic(b.url, SUM(b.count)) " +
            "FROM PageStatistic as b " +
            "WHERE b.createdAt >= :#{#fromDate} " +
            "AND b.createdAt <= :#{#toDate} GROUP BY b.url")
    List<PageStatistic> findByCreatedAtBetweenAndGroupedByName(@Param("fromDate") LocalDateTime fromDate,
                                                                  @Param("toDate") LocalDateTime to);
    @Query("SELECT new PageStatistic(b.url, sum(b.count)) " +
            "FROM PageStatistic as b GROUP BY b.url")
    List<PageStatistic> selectAllGroupedByName();
}
