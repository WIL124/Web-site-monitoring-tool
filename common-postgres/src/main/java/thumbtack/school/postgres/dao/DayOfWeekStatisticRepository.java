package thumbtack.school.postgres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.model.DayOfWeekStatistic;

@Repository
public interface DayOfWeekStatisticRepository extends JpaRepository<DayOfWeekStatistic, Long> {
}
