package kz.jumpkz.jumpapp.repository;

import java.util.List;
import java.util.Optional;
import kz.jumpkz.jumpapp.domain.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tournament entity.
 *
 * When extending this class, extend TournamentRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface TournamentRepository extends TournamentRepositoryWithBagRelationships, JpaRepository<Tournament, Long> {
    default Optional<Tournament> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Tournament> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Tournament> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
