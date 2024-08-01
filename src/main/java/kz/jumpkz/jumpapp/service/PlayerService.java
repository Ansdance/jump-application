package kz.jumpkz.jumpapp.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import kz.jumpkz.jumpapp.domain.Player;
import kz.jumpkz.jumpapp.repository.PlayerRepository;
import kz.jumpkz.jumpapp.service.dto.PlayerDTO;
import kz.jumpkz.jumpapp.service.mapper.PlayerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link kz.jumpkz.jumpapp.domain.Player}.
 */
@Service
@Transactional
public class PlayerService {

    private static final Logger log = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;

    private final PlayerMapper playerMapper;

    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    /**
     * Save a player.
     *
     * @param playerDTO the entity to save.
     * @return the persisted entity.
     */
    public PlayerDTO save(PlayerDTO playerDTO) {
        log.debug("Request to save Player : {}", playerDTO);
        Player player = playerMapper.toEntity(playerDTO);
        player = playerRepository.save(player);
        return playerMapper.toDto(player);
    }

    /**
     * Update a player.
     *
     * @param playerDTO the entity to save.
     * @return the persisted entity.
     */
    public PlayerDTO update(PlayerDTO playerDTO) {
        log.debug("Request to update Player : {}", playerDTO);
        Player player = playerMapper.toEntity(playerDTO);
        player = playerRepository.save(player);
        return playerMapper.toDto(player);
    }

    /**
     * Partially update a player.
     *
     * @param playerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlayerDTO> partialUpdate(PlayerDTO playerDTO) {
        log.debug("Request to partially update Player : {}", playerDTO);

        return playerRepository
            .findById(playerDTO.getId())
            .map(existingPlayer -> {
                playerMapper.partialUpdate(existingPlayer, playerDTO);

                return existingPlayer;
            })
            .map(playerRepository::save)
            .map(playerMapper::toDto);
    }

    /**
     * Get all the players.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PlayerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Players");
        return playerRepository.findAll(pageable).map(playerMapper::toDto);
    }

    /**
     *  Get all the players where StatisticHistory is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PlayerDTO> findAllWhereStatisticHistoryIsNull() {
        log.debug("Request to get all players where StatisticHistory is null");
        return StreamSupport.stream(playerRepository.findAll().spliterator(), false)
            .filter(player -> player.getStatisticHistory() == null)
            .map(playerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one player by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlayerDTO> findOne(Long id) {
        log.debug("Request to get Player : {}", id);
        return playerRepository.findById(id).map(playerMapper::toDto);
    }

    /**
     * Delete the player by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Player : {}", id);
        playerRepository.deleteById(id);
    }
}
