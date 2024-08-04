package kz.jumpkz.jumpapp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import kz.jumpkz.jumpapp.domain.Statistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class StatisticRepositoryWithBagRelationshipsImpl implements StatisticRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String STATISTICS_PARAMETER = "statistics";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Statistic> fetchBagRelationships(Optional<Statistic> statistic) {
        return statistic.map(this::fetchTasks);
    }

    @Override
    public Page<Statistic> fetchBagRelationships(Page<Statistic> statistics) {
        return new PageImpl<>(fetchBagRelationships(statistics.getContent()), statistics.getPageable(), statistics.getTotalElements());
    }

    @Override
    public List<Statistic> fetchBagRelationships(List<Statistic> statistics) {
        return Optional.of(statistics).map(this::fetchTasks).orElse(Collections.emptyList());
    }

    Statistic fetchTasks(Statistic result) {
        return entityManager
            .createQuery(
                "select statistic from Statistic statistic left join fetch statistic.tasks where statistic.id = :id",
                Statistic.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Statistic> fetchTasks(List<Statistic> statistics) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, statistics.size()).forEach(index -> order.put(statistics.get(index).getId(), index));
        List<Statistic> result = entityManager
            .createQuery(
                "select statistic from Statistic statistic left join fetch statistic.tasks where statistic in :statistics",
                Statistic.class
            )
            .setParameter(STATISTICS_PARAMETER, statistics)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
