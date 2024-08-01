package kz.jumpkz.jumpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import kz.jumpkz.jumpapp.domain.enumeration.Language;

/**
 * A StatisticHistory.
 */
@Entity
@Table(name = "statistic_history")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StatisticHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @JsonIgnoreProperties(value = { "tasks", "employee", "statisticHistory" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Statistic statistic;

    @JsonIgnoreProperties(value = { "location", "players", "statisticHistory" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Tournament department;

    @JsonIgnoreProperties(value = { "statistics", "manager", "tournament", "statisticHistory" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Player employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StatisticHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public StatisticHistory startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public StatisticHistory endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Language getLanguage() {
        return this.language;
    }

    public StatisticHistory language(Language language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Statistic getStatistic() {
        return this.statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    public StatisticHistory statistic(Statistic statistic) {
        this.setStatistic(statistic);
        return this;
    }

    public Tournament getDepartment() {
        return this.department;
    }

    public void setDepartment(Tournament tournament) {
        this.department = tournament;
    }

    public StatisticHistory department(Tournament tournament) {
        this.setDepartment(tournament);
        return this;
    }

    public Player getEmployee() {
        return this.employee;
    }

    public void setEmployee(Player player) {
        this.employee = player;
    }

    public StatisticHistory employee(Player player) {
        this.setEmployee(player);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatisticHistory)) {
            return false;
        }
        return getId() != null && getId().equals(((StatisticHistory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatisticHistory{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
