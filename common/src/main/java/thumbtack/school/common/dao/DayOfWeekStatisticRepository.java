package thumbtack.school.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thumbtack.school.common.model.DayOfWeekStatistic;

@Repository
public interface DayOfWeekStatisticRepository extends JpaRepository<DayOfWeekStatistic, Long> {
}
