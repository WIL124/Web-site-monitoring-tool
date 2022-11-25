package thumbtack.school.reporter.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.hbase.model.User;
import thumbtack.school.postgres.dao.UserStatisticRepository;
import thumbtack.school.postgres.model.UserStatistic;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class UserStatisticService {
    private UserStatisticRepository repository;

    public List<UserStatistic> getStatistic(List<User> users) {
        if (users.isEmpty()) {
            return Collections.emptyList();
        }
        int totalRequests = users.stream().mapToInt(user -> user.getTimestampHeadersMap().size()).sum();
        return Collections.singletonList(new UserStatistic(users.size(), totalRequests));
    }

    public void saveAll(List<UserStatistic> statistic) {
        repository.saveAll(statistic);
    }
}
