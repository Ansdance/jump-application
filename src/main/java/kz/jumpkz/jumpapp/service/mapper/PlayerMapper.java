package kz.jumpkz.jumpapp.service.mapper;

import kz.jumpkz.jumpapp.domain.Player;
import kz.jumpkz.jumpapp.domain.Team;
import kz.jumpkz.jumpapp.service.dto.PlayerDTO;
import kz.jumpkz.jumpapp.service.dto.TeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Player} and its DTO {@link PlayerDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlayerMapper extends EntityMapper<PlayerDTO, Player> {
    @Mapping(target = "manager", source = "manager", qualifiedByName = "playerId")
    @Mapping(target = "team", source = "team", qualifiedByName = "teamId")
    PlayerDTO toDto(Player s);

    @Named("playerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PlayerDTO toDtoPlayerId(Player player);

    @Named("teamId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TeamDTO toDtoTeamId(Team team);
}
