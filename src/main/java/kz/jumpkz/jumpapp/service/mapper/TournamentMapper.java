package kz.jumpkz.jumpapp.service.mapper;

import kz.jumpkz.jumpapp.domain.Location;
import kz.jumpkz.jumpapp.domain.Tournament;
import kz.jumpkz.jumpapp.service.dto.LocationDTO;
import kz.jumpkz.jumpapp.service.dto.TournamentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tournament} and its DTO {@link TournamentDTO}.
 */
@Mapper(componentModel = "spring")
public interface TournamentMapper extends EntityMapper<TournamentDTO, Tournament> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    TournamentDTO toDto(Tournament s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);
}
