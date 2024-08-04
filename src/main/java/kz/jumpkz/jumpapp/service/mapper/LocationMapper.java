package kz.jumpkz.jumpapp.service.mapper;

import kz.jumpkz.jumpapp.domain.Country;
import kz.jumpkz.jumpapp.domain.Location;
import kz.jumpkz.jumpapp.service.dto.CountryDTO;
import kz.jumpkz.jumpapp.service.dto.LocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countryId")
    LocationDTO toDto(Location s);

    @Named("countryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountryDTO toDtoCountryId(Country country);
}
