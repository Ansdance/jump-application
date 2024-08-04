package kz.jumpkz.jumpapp.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import kz.jumpkz.jumpapp.domain.Team;
import kz.jumpkz.jumpapp.domain.Tournament;
import kz.jumpkz.jumpapp.service.dto.TeamDTO;
import kz.jumpkz.jumpapp.service.dto.TournamentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Team} and its DTO {@link TeamDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {
    @Mapping(target = "tournaments", source = "tournaments", qualifiedByName = "tournamentIdSet")
    TeamDTO toDto(Team s);

    @Mapping(target = "tournaments", ignore = true)
    @Mapping(target = "removeTournament", ignore = true)
    Team toEntity(TeamDTO teamDTO);

    @Named("tournamentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TournamentDTO toDtoTournamentId(Tournament tournament);

    @Named("tournamentIdSet")
    default Set<TournamentDTO> toDtoTournamentIdSet(Set<Tournament> tournament) {
        return tournament.stream().map(this::toDtoTournamentId).collect(Collectors.toSet());
    }
}
