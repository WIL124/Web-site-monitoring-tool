package thumbtack.school.postgres.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thumbtack.school.postgres.model.PageStatistic;

@Repository
public interface PageStatisticRepository extends JpaRepository<PageStatistic, Long> {
}
