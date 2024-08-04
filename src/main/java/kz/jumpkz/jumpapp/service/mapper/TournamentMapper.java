package kz.jumpkz.jumpapp.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import kz.jumpkz.jumpapp.domain.Location;
import kz.jumpkz.jumpapp.domain.Team;
import kz.jumpkz.jumpapp.domain.Tournament;
import kz.jumpkz.jumpapp.service.dto.LocationDTO;
import kz.jumpkz.jumpapp.service.dto.TeamDTO;
import kz.jumpkz.jumpapp.service.dto.TournamentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tournament} and its DTO {@link TournamentDTO}.
 */
@Mapper(componentModel = "spring")
public interface TournamentMapper extends EntityMapper<TournamentDTO, Tournament> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    @Mapping(target = "teams", source = "teams", qualifiedByName = "teamIdSet")
    TournamentDTO toDto(Tournament s);

    @Mapping(target = "removeTeam", ignore = true)
    Tournament toEntity(TournamentDTO tournamentDTO);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);

    @Named("teamId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TeamDTO toDtoTeamId(Team team);

    @Named("teamIdSet")
    default Set<TeamDTO> toDtoTeamIdSet(Set<Team> team) {
        return team.stream().map(this::toDtoTeamId).collect(Collectors.toSet());
    }
}
