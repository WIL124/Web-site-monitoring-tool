package thumbtack.school.reporter.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thumbtack.school.reporter.model.TimeOfDayStatistic;

@Repository
public interface TimeOfDayStatisticRepository extends JpaRepository<TimeOfDayStatistic, Long> {
}
