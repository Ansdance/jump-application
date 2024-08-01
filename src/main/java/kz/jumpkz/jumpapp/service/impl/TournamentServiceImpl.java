package kz.jumpkz.jumpapp.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import kz.jumpkz.jumpapp.domain.Tournament;
import kz.jumpkz.jumpapp.repository.TournamentRepository;
import kz.jumpkz.jumpapp.service.TournamentService;
import kz.jumpkz.jumpapp.service.dto.TournamentDTO;
import kz.jumpkz.jumpapp.service.mapper.TournamentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link kz.jumpkz.jumpapp.domain.Tournament}.
 */
@Service
@Transactional
public class TournamentServiceImpl implements TournamentService {

    private static final Logger log = LoggerFactory.getLogger(TournamentServiceImpl.class);

    private final TournamentRepository tournamentRepository;

    private final TournamentMapper tournamentMapper;

    public TournamentServiceImpl(TournamentRepository tournamentRepository, TournamentMapper tournamentMapper) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentMapper = tournamentMapper;
    }

    @Override
    public TournamentDTO save(TournamentDTO tournamentDTO) {
        log.debug("Request to save Tournament : {}", tournamentDTO);
        Tournament tournament = tournamentMapper.toEntity(tournamentDTO);
        tournament = tournamentRepository.save(tournament);
        return tournamentMapper.toDto(tournament);
    }

    @Override
    public TournamentDTO update(TournamentDTO tournamentDTO) {
        log.debug("Request to update Tournament : {}", tournamentDTO);
        Tournament tournament = tournamentMapper.toEntity(tournamentDTO);
        tournament = tournamentRepository.save(tournament);
        return tournamentMapper.toDto(tournament);
    }

    @Override
    public Optional<TournamentDTO> partialUpdate(TournamentDTO tournamentDTO) {
        log.debug("Request to partially update Tournament : {}", tournamentDTO);

        return tournamentRepository
            .findById(tournamentDTO.getId())
            .map(existingTournament -> {
                tournamentMapper.partialUpdate(existingTournament, tournamentDTO);

                return existingTournament;
            })
            .map(tournamentRepository::save)
            .map(tournamentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TournamentDTO> findAll() {
        log.debug("Request to get all Tournaments");
        return tournamentRepository.findAll().stream().map(tournamentMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the tournaments where StatisticHistory is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TournamentDTO> findAllWhereStatisticHistoryIsNull() {
        log.debug("Request to get all tournaments where StatisticHistory is null");
        return StreamSupport.stream(tournamentRepository.findAll().spliterator(), false)
            .filter(tournament -> tournament.getStatisticHistory() == null)
            .map(tournamentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TournamentDTO> findOne(Long id) {
        log.debug("Request to get Tournament : {}", id);
        return tournamentRepository.findById(id).map(tournamentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tournament : {}", id);
        tournamentRepository.deleteById(id);
    }
}
