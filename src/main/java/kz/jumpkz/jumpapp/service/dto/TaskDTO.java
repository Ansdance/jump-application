package kz.jumpkz.jumpapp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link kz.jumpkz.jumpapp.domain.Task} entity.
 */
@Schema(description = "Task entity.\n@author The JHipster team.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaskDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private Set<StatisticDTO> statistics = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<StatisticDTO> getStatistics() {
        return statistics;
    }

    public void setStatistics(Set<StatisticDTO> statistics) {
        this.statistics = statistics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskDTO)) {
            return false;
        }

        TaskDTO taskDTO = (TaskDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taskDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", statistics=" + getStatistics() +
            "}";
    }
}
