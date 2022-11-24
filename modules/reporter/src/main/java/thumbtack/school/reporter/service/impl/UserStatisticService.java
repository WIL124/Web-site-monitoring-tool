package thumbtack.school.reporter.service.impl;

import org.springframework.stereotype.Service;
import thumbtack.school.hbase.model.User;
import thumbtack.school.postgres.dao.UserStatisticRepository;
import thumbtack.school.postgres.model.UserStatistic;
import thumbtack.school.reporter.service.AbstractStatisticService;

import java.util.Collections;
import java.util.List;

@Service
public class UserStatisticService extends AbstractStatisticService<UserStatistic, UserStatisticRepository> {
    public UserStatisticService(UserStatisticRepository repository) {
        super(repository);
    }

    @Override
    public List<UserStatistic> getStatistic(List<User> users) {
        return Collections.singletonList(new UserStatistic("Unique users", users.size()));
    }
}
