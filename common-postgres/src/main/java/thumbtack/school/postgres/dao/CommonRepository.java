package thumbtack.school.postgres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import thumbtack.school.postgres.model.AbstractStatistic;

import java.time.LocalDateTime;
import java.util.List;

@NoRepositoryBean
public interface CommonRepository<E extends AbstractStatistic> extends JpaRepository<E, Long> {
    List<E> selectAllGroupedByName();

    List<E> findByCreatedAtBetweenAndGroupedByName(@Param("fromDate") LocalDateTime fromDate,
                                                   @Param("toDate") LocalDateTime to);

    List<E> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}
