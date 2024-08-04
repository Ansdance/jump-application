package kz.jumpkz.jumpapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import kz.jumpkz.jumpapp.domain.enumeration.Language;

/**
 * A DTO for the {@link kz.jumpkz.jumpapp.domain.StatisticHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StatisticHistoryDTO implements Serializable {

    private Long id;

    private Instant startDate;

    private Instant endDate;

    private Language language;

    private StatisticDTO statistic;

    private TournamentDTO department;

    private PlayerDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public StatisticDTO getStatistic() {
        return statistic;
    }

    public void setStatistic(StatisticDTO statistic) {
        this.statistic = statistic;
    }

    public TournamentDTO getDepartment() {
        return department;
    }

    public void setDepartment(TournamentDTO department) {
        this.department = department;
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
        if (!(o instanceof StatisticHistoryDTO)) {
            return false;
        }

        StatisticHistoryDTO statisticHistoryDTO = (StatisticHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, statisticHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatisticHistoryDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", language='" + getLanguage() + "'" +
            ", statistic=" + getStatistic() +
            ", department=" + getDepartment() +
            ", employee=" + getEmployee() +
            "}";
    }
}
