package kz.jumpkz.jumpapp.service;

import java.util.List;
import java.util.Optional;
import kz.jumpkz.jumpapp.service.dto.RegionDTO;

/**
 * Service Interface for managing {@link kz.jumpkz.jumpapp.domain.Region}.
 */
public interface RegionService {
    /**
     * Save a region.
     *
     * @param regionDTO the entity to save.
     * @return the persisted entity.
     */
    RegionDTO save(RegionDTO regionDTO);

    /**
     * Updates a region.
     *
     * @param regionDTO the entity to update.
     * @return the persisted entity.
     */
    RegionDTO update(RegionDTO regionDTO);

    /**
     * Partially updates a region.
     *
     * @param regionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RegionDTO> partialUpdate(RegionDTO regionDTO);

    /**
     * Get all the regions.
     *
     * @return the list of entities.
     */
    List<RegionDTO> findAll();

    /**
     * Get all the RegionDTO where Country is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<RegionDTO> findAllWhereCountryIsNull();

    /**
     * Get the "id" region.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegionDTO> findOne(Long id);

    /**
     * Delete the "id" region.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
