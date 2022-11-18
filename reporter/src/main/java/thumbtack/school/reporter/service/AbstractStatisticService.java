package thumbtack.school.reporter.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.hbase.model.User;
import thumbtack.school.postgres.dao.CommonRepository;
import thumbtack.school.postgres.model.AbstractStatistic;

import java.util.List;

@Service
@AllArgsConstructor
public abstract class AbstractStatisticService<E extends AbstractStatistic, R extends CommonRepository<E>>
        implements StatisticService<E> {
    protected R repository;

    public void saveAll(List<E> statistic) {
        repository.saveAll(statistic);
    }

    public abstract List<E> getStatistic(List<User> users);
}
