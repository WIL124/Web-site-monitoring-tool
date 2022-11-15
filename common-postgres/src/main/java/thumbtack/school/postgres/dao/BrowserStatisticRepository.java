package thumbtack.school.postgres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.model.BrowserStatistic;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BrowserStatisticRepository extends JpaRepository<BrowserStatistic, Long> {
    List<BrowserStatistic> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT new BrowserStatistic(b.name, SUM(b.count)) " +
            "FROM BrowserStatistic as b " +
            "WHERE b.createdAt >= :#{#fromDate} " +
            "AND b.createdAt <= :#{#toDate} GROUP BY b.name")
    List<BrowserStatistic> findByCreatedAtBetweenAndGroupedByName(@Param("fromDate") LocalDateTime fromDate,
                                                                  @Param("toDate") LocalDateTime to);
    @Query("SELECT new BrowserStatistic(b.name, sum(b.count)) " +
            "FROM BrowserStatistic as b GROUP BY b.name")
    List<BrowserStatistic> selectAllGroupedByName();
}
