package kz.jumpkz.jumpapp.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import kz.jumpkz.jumpapp.domain.Statistic;
import kz.jumpkz.jumpapp.domain.Task;
import kz.jumpkz.jumpapp.service.dto.StatisticDTO;
import kz.jumpkz.jumpapp.service.dto.TaskDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring")
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {
    @Mapping(target = "statistics", source = "statistics", qualifiedByName = "statisticIdSet")
    TaskDTO toDto(Task s);

    @Mapping(target = "statistics", ignore = true)
    @Mapping(target = "removeStatistic", ignore = true)
    Task toEntity(TaskDTO taskDTO);

    @Named("statisticId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StatisticDTO toDtoStatisticId(Statistic statistic);

    @Named("statisticIdSet")
    default Set<StatisticDTO> toDtoStatisticIdSet(Set<Statistic> statistic) {
        return statistic.stream().map(this::toDtoStatisticId).collect(Collectors.toSet());
    }
}
