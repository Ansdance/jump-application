package kz.jumpkz.jumpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Statistic.
 */
@Entity
@Table(name = "statistic")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Statistic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "statistic_title")
    private String statisticTitle;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_statistic__task",
        joinColumns = @JoinColumn(name = "statistic_id"),
        inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    @JsonIgnoreProperties(value = { "statistics" }, allowSetters = true)
    private Set<Task> tasks = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "statistics", "manager", "team", "statisticHistory" }, allowSetters = true)
    private Player employee;

    @JsonIgnoreProperties(value = { "statistic", "department", "employee" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "statistic")
    private StatisticHistory statisticHistory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Statistic id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatisticTitle() {
        return this.statisticTitle;
    }

    public Statistic statisticTitle(String statisticTitle) {
        this.setStatisticTitle(statisticTitle);
        return this;
    }

    public void setStatisticTitle(String statisticTitle) {
        this.statisticTitle = statisticTitle;
    }

    public Set<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Statistic tasks(Set<Task> tasks) {
        this.setTasks(tasks);
        return this;
    }

    public Statistic addTask(Task task) {
        this.tasks.add(task);
        return this;
    }

    public Statistic removeTask(Task task) {
        this.tasks.remove(task);
        return this;
    }

    public Player getEmployee() {
        return this.employee;
    }

    public void setEmployee(Player player) {
        this.employee = player;
    }

    public Statistic employee(Player player) {
        this.setEmployee(player);
        return this;
    }

    public StatisticHistory getStatisticHistory() {
        return this.statisticHistory;
    }

    public void setStatisticHistory(StatisticHistory statisticHistory) {
        if (this.statisticHistory != null) {
            this.statisticHistory.setStatistic(null);
        }
        if (statisticHistory != null) {
            statisticHistory.setStatistic(this);
        }
        this.statisticHistory = statisticHistory;
    }

    public Statistic statisticHistory(StatisticHistory statisticHistory) {
        this.setStatisticHistory(statisticHistory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Statistic)) {
            return false;
        }
        return getId() != null && getId().equals(((Statistic) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Statistic{" +
            "id=" + getId() +
            ", statisticTitle='" + getStatisticTitle() + "'" +
            "}";
    }
}
