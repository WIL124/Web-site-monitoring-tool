package thumbtack.school.reporter.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thumbtack.school.reporter.model.DayOfWeekStatistic;

@Repository
public interface DayOfWeekStatisticRepository extends JpaRepository<DayOfWeekStatistic, Long> {
}
