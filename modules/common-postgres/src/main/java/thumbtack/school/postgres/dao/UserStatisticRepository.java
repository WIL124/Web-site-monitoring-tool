package thumbtack.school.postgres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.model.UserStatistic;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserStatisticRepository extends JpaRepository<UserStatistic, Long> {
    public List<UserStatistic> getAllByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}
