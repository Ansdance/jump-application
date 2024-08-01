package kz.jumpkz.jumpapp.repository;

import java.util.List;
import java.util.Optional;
import kz.jumpkz.jumpapp.domain.Statistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Statistic entity.
 *
 * When extending this class, extend StatisticRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface StatisticRepository extends StatisticRepositoryWithBagRelationships, JpaRepository<Statistic, Long> {
    default Optional<Statistic> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Statistic> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Statistic> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
