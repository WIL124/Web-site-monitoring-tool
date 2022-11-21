package thumbtack.school.reporter.service;

import thumbtack.school.hbase.model.User;
import thumbtack.school.postgres.model.AbstractStatistic;

import java.util.List;

public interface StatisticService<T extends AbstractStatistic> {
    List<T> getStatistic(List<User> users);

    void saveAll(List<T> statistic);
}
