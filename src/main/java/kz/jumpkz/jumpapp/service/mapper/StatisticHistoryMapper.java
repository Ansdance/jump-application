package kz.jumpkz.jumpapp.service.mapper;

import kz.jumpkz.jumpapp.domain.Player;
import kz.jumpkz.jumpapp.domain.Statistic;
import kz.jumpkz.jumpapp.domain.StatisticHistory;
import kz.jumpkz.jumpapp.domain.Tournament;
import kz.jumpkz.jumpapp.service.dto.PlayerDTO;
import kz.jumpkz.jumpapp.service.dto.StatisticDTO;
import kz.jumpkz.jumpapp.service.dto.StatisticHistoryDTO;
import kz.jumpkz.jumpapp.service.dto.TournamentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StatisticHistory} and its DTO {@link StatisticHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface StatisticHistoryMapper extends EntityMapper<StatisticHistoryDTO, StatisticHistory> {
    @Mapping(target = "statistic", source = "statistic", qualifiedByName = "statisticId")
    @Mapping(target = "department", source = "department", qualifiedByName = "tournamentId")
    @Mapping(target = "employee", source = "employee", qualifiedByName = "playerId")
    StatisticHistoryDTO toDto(StatisticHistory s);

    @Named("statisticId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StatisticDTO toDtoStatisticId(Statistic statistic);

    @Named("tournamentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TournamentDTO toDtoTournamentId(Tournament tournament);

    @Named("playerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PlayerDTO toDtoPlayerId(Player player);
}
