package thumbtack.school.reporter.service;

import thumbtack.school.tracking.model.User;

import java.util.List;

public interface StatisticService<T> {
    List<T> getStatistic(List<User> users);

    void saveAll(List<T> statistic);
}
