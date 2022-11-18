package thumbtack.school.postgres.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.model.PageStatistic;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PageStatisticRepository extends CommonRepository<PageStatistic> {
    List<PageStatistic> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT new PageStatistic(b.name, SUM(b.count)) " +
            "FROM PageStatistic as b " +
            "WHERE b.createdAt >= :#{#fromDate} " +
            "AND b.createdAt <= :#{#toDate} GROUP BY b.name")
    List<PageStatistic> findByCreatedAtBetweenAndGroupedByName(@Param("fromDate") LocalDateTime fromDate,
                                                               @Param("toDate") LocalDateTime to);

    @Query("SELECT new PageStatistic(b.name, sum(b.count)) " +
            "FROM PageStatistic as b GROUP BY b.name")
    List<PageStatistic> selectAllGroupedByName();
}
