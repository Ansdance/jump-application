package kz.jumpkz.jumpapp.service.mapper;

import kz.jumpkz.jumpapp.domain.Country;
import kz.jumpkz.jumpapp.domain.Region;
import kz.jumpkz.jumpapp.service.dto.CountryDTO;
import kz.jumpkz.jumpapp.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Country} and its DTO {@link CountryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CountryMapper extends EntityMapper<CountryDTO, Country> {
    @Mapping(target = "region", source = "region", qualifiedByName = "regionId")
    CountryDTO toDto(Country s);

    @Named("regionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RegionDTO toDtoRegionId(Region region);
}
