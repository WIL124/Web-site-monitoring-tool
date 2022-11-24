package thumbtack.school.api.service;

import org.springframework.stereotype.Service;
import thumbtack.school.postgres.dao.UserStatisticRepository;
import thumbtack.school.postgres.model.UserStatistic;

@Service
public class UserStatisticService extends AbstractStatisticService<UserStatistic, UserStatisticRepository> {
    public UserStatisticService(UserStatisticRepository repository) {
        super(repository);
    }
}

