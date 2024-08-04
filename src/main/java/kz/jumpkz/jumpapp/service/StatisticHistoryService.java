package kz.jumpkz.jumpapp.service;

import java.util.Optional;
import kz.jumpkz.jumpapp.service.dto.StatisticHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link kz.jumpkz.jumpapp.domain.StatisticHistory}.
 */
public interface StatisticHistoryService {
    /**
     * Save a statisticHistory.
     *
     * @param statisticHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    StatisticHistoryDTO save(StatisticHistoryDTO statisticHistoryDTO);

    /**
     * Updates a statisticHistory.
     *
     * @param statisticHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    StatisticHistoryDTO update(StatisticHistoryDTO statisticHistoryDTO);

    /**
     * Partially updates a statisticHistory.
     *
     * @param statisticHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StatisticHistoryDTO> partialUpdate(StatisticHistoryDTO statisticHistoryDTO);

    /**
     * Get all the statisticHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StatisticHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" statisticHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StatisticHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" statisticHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
