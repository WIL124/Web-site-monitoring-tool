package thumbtack.school.reporter.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thumbtack.school.reporter.model.statistic.BrowserStatistic;

@Repository
public interface BrowserStatisticRepository extends JpaRepository<BrowserStatistic, Long> {
}
