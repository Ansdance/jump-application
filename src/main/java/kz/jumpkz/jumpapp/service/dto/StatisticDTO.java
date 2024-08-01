package kz.jumpkz.jumpapp.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link kz.jumpkz.jumpapp.domain.Statistic} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StatisticDTO implements Serializable {

    private Long id;

    private String statisticTitle;

    private Set<TaskDTO> tasks = new HashSet<>();

    private PlayerDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatisticTitle() {
        return statisticTitle;
    }

    public void setStatisticTitle(String statisticTitle) {
        this.statisticTitle = statisticTitle;
    }

    public Set<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskDTO> tasks) {
        this.tasks = tasks;
    }

    public PlayerDTO getEmployee() {
        return employee;
    }

    public void setEmployee(PlayerDTO employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatisticDTO)) {
            return false;
        }

        StatisticDTO statisticDTO = (StatisticDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, statisticDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatisticDTO{" +
            "id=" + getId() +
            ", statisticTitle='" + getStatisticTitle() + "'" +
            ", tasks=" + getTasks() +
            ", employee=" + getEmployee() +
            "}";
    }
}
