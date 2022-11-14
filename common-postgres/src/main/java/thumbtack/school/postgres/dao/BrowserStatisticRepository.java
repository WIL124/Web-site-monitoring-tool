package thumbtack.school.postgres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.model.BrowserStatistic;

@Repository
public interface BrowserStatisticRepository extends JpaRepository<BrowserStatistic, Long> {
}
