package kz.jumpkz.jumpapp.service.mapper;

import kz.jumpkz.jumpapp.domain.Region;
import kz.jumpkz.jumpapp.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Region} and its DTO {@link RegionDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {}
