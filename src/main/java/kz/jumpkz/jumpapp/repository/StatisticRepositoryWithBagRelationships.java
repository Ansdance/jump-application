package kz.jumpkz.jumpapp.repository;

import java.util.List;
import java.util.Optional;
import kz.jumpkz.jumpapp.domain.Statistic;
import org.springframework.data.domain.Page;

public interface StatisticRepositoryWithBagRelationships {
    Optional<Statistic> fetchBagRelationships(Optional<Statistic> statistic);

    List<Statistic> fetchBagRelationships(List<Statistic> statistics);

    Page<Statistic> fetchBagRelationships(Page<Statistic> statistics);
}
