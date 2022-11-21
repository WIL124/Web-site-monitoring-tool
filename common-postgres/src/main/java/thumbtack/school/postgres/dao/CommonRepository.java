package thumbtack.school.postgres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import thumbtack.school.postgres.dto.StatisticDto;
import thumbtack.school.postgres.model.AbstractStatistic;

import java.time.LocalDateTime;
import java.util.List;

@NoRepositoryBean
public interface CommonRepository<E extends AbstractStatistic>
        extends JpaRepository<E, Long> {
    List<StatisticDto> getStatisticGrouped(LocalDateTime from, LocalDateTime to);
}
