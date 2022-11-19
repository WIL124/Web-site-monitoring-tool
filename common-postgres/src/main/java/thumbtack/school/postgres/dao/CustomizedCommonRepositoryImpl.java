package thumbtack.school.postgres.dao;

import org.springframework.stereotype.Component;
import thumbtack.school.postgres.dto.StatisticDto;
import thumbtack.school.postgres.model.AbstractStatistic;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomizedCommonRepositoryImpl implements CustomizedCommonRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<StatisticDto> selectAllCustom(LocalDateTime from, LocalDateTime to) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StatisticDto> query = cb.createQuery(StatisticDto.class);
        Root<AbstractStatistic> abstractStatisticRoot = query.from(AbstractStatistic.class);
        Path<LocalDateTime> createdAtPath = abstractStatisticRoot.get("createdAt");

        List<Predicate> predicates = new ArrayList<>();
        if (from != null) {
            predicates.add(cb.greaterThanOrEqualTo(createdAtPath, from));
        }
        if (to != null) {
            predicates.add(cb.lessThanOrEqualTo(createdAtPath, to));
        }

        query.multiselect(abstractStatisticRoot.get("name").alias("name"),
                        cb.sum(abstractStatisticRoot.get("count")).alias("count"))
                .where(cb.and(predicates.toArray(new Predicate[predicates.size()])))
                .groupBy(abstractStatisticRoot.get("name"));

        return entityManager.createQuery(query).getResultList();
    }
}
