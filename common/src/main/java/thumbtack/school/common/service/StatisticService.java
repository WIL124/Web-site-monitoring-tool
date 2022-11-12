package thumbtack.school.common.service;

import thumbtack.school.common.model.User;

import java.util.List;

public interface StatisticService<T> {
    List<T> getStatistic(List<User> users);

    void saveAll(List<T> statistic);
}
