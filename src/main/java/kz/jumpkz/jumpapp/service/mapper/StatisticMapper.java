package kz.jumpkz.jumpapp.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import kz.jumpkz.jumpapp.domain.Player;
import kz.jumpkz.jumpapp.domain.Statistic;
import kz.jumpkz.jumpapp.domain.Task;
import kz.jumpkz.jumpapp.service.dto.PlayerDTO;
import kz.jumpkz.jumpapp.service.dto.StatisticDTO;
import kz.jumpkz.jumpapp.service.dto.TaskDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Statistic} and its DTO {@link StatisticDTO}.
 */
@Mapper(componentModel = "spring")
public interface StatisticMapper extends EntityMapper<StatisticDTO, Statistic> {
    @Mapping(target = "tasks", source = "tasks", qualifiedByName = "taskTitleSet")
    @Mapping(target = "employee", source = "employee", qualifiedByName = "playerId")
    StatisticDTO toDto(Statistic s);

    @Mapping(target = "removeTask", ignore = true)
    Statistic toEntity(StatisticDTO statisticDTO);

    @Named("taskTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    TaskDTO toDtoTaskTitle(Task task);

    @Named("taskTitleSet")
    default Set<TaskDTO> toDtoTaskTitleSet(Set<Task> task) {
        return task.stream().map(this::toDtoTaskTitle).collect(Collectors.toSet());
    }

    @Named("playerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PlayerDTO toDtoPlayerId(Player player);
}
