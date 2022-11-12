package thumbtack.school.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thumbtack.school.common.model.BrowserStatistic;

@Repository
public interface BrowserStatisticRepository extends JpaRepository<BrowserStatistic, Long> {
}
