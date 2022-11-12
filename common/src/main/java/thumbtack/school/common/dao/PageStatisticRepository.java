package thumbtack.school.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thumbtack.school.common.model.PageStatistic;

@Repository
public interface PageStatisticRepository extends JpaRepository<PageStatistic, Long> {
}
