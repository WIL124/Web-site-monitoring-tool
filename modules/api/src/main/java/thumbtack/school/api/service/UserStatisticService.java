package thumbtack.school.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import thumbtack.school.postgres.dao.UserStatisticRepository;
import thumbtack.school.postgres.model.UserStatistic;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserStatisticService {
    private UserStatisticRepository repository;

    public List<UserStatistic> getAll(LocalDate from, LocalDate to) {
        from = (from == null) ? LocalDate.EPOCH : from;
        to = (to == null) ? LocalDate.of(2100, 1, 1) : to;
        return repository.getAllByCreatedAtBetween(from.atStartOfDay(), to.atTime(LocalTime.MAX));
    }
}

