package kz.jumpkz.jumpapp.service;

import java.util.List;
import java.util.Optional;
import kz.jumpkz.jumpapp.service.dto.TournamentDTO;

/**
 * Service Interface for managing {@link kz.jumpkz.jumpapp.domain.Tournament}.
 */
public interface TournamentService {
    /**
     * Save a tournament.
     *
     * @param tournamentDTO the entity to save.
     * @return the persisted entity.
     */
    TournamentDTO save(TournamentDTO tournamentDTO);

    /**
     * Updates a tournament.
     *
     * @param tournamentDTO the entity to update.
     * @return the persisted entity.
     */
    TournamentDTO update(TournamentDTO tournamentDTO);

    /**
     * Partially updates a tournament.
     *
     * @param tournamentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TournamentDTO> partialUpdate(TournamentDTO tournamentDTO);

    /**
     * Get all the tournaments.
     *
     * @return the list of entities.
     */
    List<TournamentDTO> findAll();

    /**
     * Get all the TournamentDTO where StatisticHistory is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<TournamentDTO> findAllWhereStatisticHistoryIsNull();

    /**
     * Get the "id" tournament.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TournamentDTO> findOne(Long id);

    /**
     * Delete the "id" tournament.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
