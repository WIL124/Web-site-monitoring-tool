package thumbtack.school.postgres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.model.TimeOfDayStatistic;

@Repository
public interface TimeOfDayStatisticRepository extends JpaRepository<TimeOfDayStatistic, Long> {
}
