package kz.jumpkz.jumpapp.service.impl;

import java.util.Optional;
import kz.jumpkz.jumpapp.domain.StatisticHistory;
import kz.jumpkz.jumpapp.repository.StatisticHistoryRepository;
import kz.jumpkz.jumpapp.service.StatisticHistoryService;
import kz.jumpkz.jumpapp.service.dto.StatisticHistoryDTO;
import kz.jumpkz.jumpapp.service.mapper.StatisticHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link kz.jumpkz.jumpapp.domain.StatisticHistory}.
 */
@Service
@Transactional
public class StatisticHistoryServiceImpl implements StatisticHistoryService {

    private static final Logger log = LoggerFactory.getLogger(StatisticHistoryServiceImpl.class);

    private final StatisticHistoryRepository statisticHistoryRepository;

    private final StatisticHistoryMapper statisticHistoryMapper;

    public StatisticHistoryServiceImpl(
        StatisticHistoryRepository statisticHistoryRepository,
        StatisticHistoryMapper statisticHistoryMapper
    ) {
        this.statisticHistoryRepository = statisticHistoryRepository;
        this.statisticHistoryMapper = statisticHistoryMapper;
    }

    @Override
    public StatisticHistoryDTO save(StatisticHistoryDTO statisticHistoryDTO) {
        log.debug("Request to save StatisticHistory : {}", statisticHistoryDTO);
        StatisticHistory statisticHistory = statisticHistoryMapper.toEntity(statisticHistoryDTO);
        statisticHistory = statisticHistoryRepository.save(statisticHistory);
        return statisticHistoryMapper.toDto(statisticHistory);
    }

    @Override
    public StatisticHistoryDTO update(StatisticHistoryDTO statisticHistoryDTO) {
        log.debug("Request to update StatisticHistory : {}", statisticHistoryDTO);
        StatisticHistory statisticHistory = statisticHistoryMapper.toEntity(statisticHistoryDTO);
        statisticHistory = statisticHistoryRepository.save(statisticHistory);
        return statisticHistoryMapper.toDto(statisticHistory);
    }

    @Override
    public Optional<StatisticHistoryDTO> partialUpdate(StatisticHistoryDTO statisticHistoryDTO) {
        log.debug("Request to partially update StatisticHistory : {}", statisticHistoryDTO);

        return statisticHistoryRepository
            .findById(statisticHistoryDTO.getId())
            .map(existingStatisticHistory -> {
                statisticHistoryMapper.partialUpdate(existingStatisticHistory, statisticHistoryDTO);

                return existingStatisticHistory;
            })
            .map(statisticHistoryRepository::save)
            .map(statisticHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StatisticHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StatisticHistories");
        return statisticHistoryRepository.findAll(pageable).map(statisticHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StatisticHistoryDTO> findOne(Long id) {
        log.debug("Request to get StatisticHistory : {}", id);
        return statisticHistoryRepository.findById(id).map(statisticHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StatisticHistory : {}", id);
        statisticHistoryRepository.deleteById(id);
    }
}
