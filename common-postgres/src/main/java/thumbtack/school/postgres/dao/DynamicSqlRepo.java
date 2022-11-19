package thumbtack.school.postgres.dao;

import thumbtack.school.postgres.model.AbstractStatistic;

import java.time.LocalDateTime;
import java.util.List;

public interface DynamicSqlRepo<E extends AbstractStatistic> {
    List<E> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}
