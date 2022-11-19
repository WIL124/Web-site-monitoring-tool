package thumbtack.school.postgres.dao;

import thumbtack.school.postgres.model.AbstractStatistic;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DynamicSqlRepositoryImpl implements DynamicSqlRepo<AbstractStatistic> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AbstractStatistic> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AbstractStatistic> query = cb.createQuery(AbstractStatistic.class);
        Root<AbstractStatistic> abstractStatisticRoot = query.from(AbstractStatistic.class);
        Path<LocalDateTime> createdAtPath = abstractStatisticRoot.get("createdAt");
        List<Predicate> predicates = new ArrayList<>();
        if (from != null) {
            predicates.add(cb.greaterThanOrEqualTo(createdAtPath, from));
        }
        if (to != null) {
            predicates.add(cb.lessThanOrEqualTo(createdAtPath, to));
        }
        query.select(abstractStatisticRoot).where(cb.and(predicates.toArray(new
                Predicate[predicates.size()])));
        return entityManager.createQuery(query).getResultList();
    }
}
