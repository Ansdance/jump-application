package kz.jumpkz.jumpapp.repository;

import java.util.List;
import java.util.Optional;
import kz.jumpkz.jumpapp.domain.Tournament;
import org.springframework.data.domain.Page;

public interface TournamentRepositoryWithBagRelationships {
    Optional<Tournament> fetchBagRelationships(Optional<Tournament> tournament);

    List<Tournament> fetchBagRelationships(List<Tournament> tournaments);

    Page<Tournament> fetchBagRelationships(Page<Tournament> tournaments);
}
