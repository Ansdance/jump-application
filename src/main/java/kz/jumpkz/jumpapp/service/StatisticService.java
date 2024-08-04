package kz.jumpkz.jumpapp.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import kz.jumpkz.jumpapp.domain.Statistic;
import kz.jumpkz.jumpapp.repository.StatisticRepository;
import kz.jumpkz.jumpapp.service.dto.StatisticDTO;
import kz.jumpkz.jumpapp.service.mapper.StatisticMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link kz.jumpkz.jumpapp.domain.Statistic}.
 */
@Service
@Transactional
public class StatisticService {

    private static final Logger log = LoggerFactory.getLogger(StatisticService.class);

    private final StatisticRepository statisticRepository;

    private final StatisticMapper statisticMapper;

    public StatisticService(StatisticRepository statisticRepository, StatisticMapper statisticMapper) {
        this.statisticRepository = statisticRepository;
        this.statisticMapper = statisticMapper;
    }

    /**
     * Save a statistic.
     *
     * @param statisticDTO the entity to save.
     * @return the persisted entity.
     */
    public StatisticDTO save(StatisticDTO statisticDTO) {
        log.debug("Request to save Statistic : {}", statisticDTO);
        Statistic statistic = statisticMapper.toEntity(statisticDTO);
        statistic = statisticRepository.save(statistic);
        return statisticMapper.toDto(statistic);
    }

    /**
     * Update a statistic.
     *
     * @param statisticDTO the entity to save.
     * @return the persisted entity.
     */
    public StatisticDTO update(StatisticDTO statisticDTO) {
        log.debug("Request to update Statistic : {}", statisticDTO);
        Statistic statistic = statisticMapper.toEntity(statisticDTO);
        statistic = statisticRepository.save(statistic);
        return statisticMapper.toDto(statistic);
    }

    /**
     * Partially update a statistic.
     *
     * @param statisticDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StatisticDTO> partialUpdate(StatisticDTO statisticDTO) {
        log.debug("Request to partially update Statistic : {}", statisticDTO);

        return statisticRepository
            .findById(statisticDTO.getId())
            .map(existingStatistic -> {
                statisticMapper.partialUpdate(existingStatistic, statisticDTO);

                return existingStatistic;
            })
            .map(statisticRepository::save)
            .map(statisticMapper::toDto);
    }

    /**
     * Get all the statistics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StatisticDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Statistics");
        return statisticRepository.findAll(pageable).map(statisticMapper::toDto);
    }

    /**
     * Get all the statistics with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<StatisticDTO> findAllWithEagerRelationships(Pageable pageable) {
        return statisticRepository.findAllWithEagerRelationships(pageable).map(statisticMapper::toDto);
    }

    /**
     *  Get all the statistics where StatisticHistory is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StatisticDTO> findAllWhereStatisticHistoryIsNull() {
        log.debug("Request to get all statistics where StatisticHistory is null");
        return StreamSupport.stream(statisticRepository.findAll().spliterator(), false)
            .filter(statistic -> statistic.getStatisticHistory() == null)
            .map(statisticMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one statistic by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StatisticDTO> findOne(Long id) {
        log.debug("Request to get Statistic : {}", id);
        return statisticRepository.findOneWithEagerRelationships(id).map(statisticMapper::toDto);
    }

    /**
     * Delete the statistic by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Statistic : {}", id);
        statisticRepository.deleteById(id);
    }
}
