package thumbtack.school.postgres.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import thumbtack.school.postgres.dto.StatisticDto;
import thumbtack.school.postgres.model.AbstractStatistic;

import java.time.LocalDateTime;
import java.util.List;

@NoRepositoryBean
public interface CommonRepository<E extends AbstractStatistic>
        extends CrudRepository<E, Long>, CustomizedCommonRepository {
    List<StatisticDto> selectAllCustom(LocalDateTime fromDate, LocalDateTime to);
}
