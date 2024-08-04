package kz.jumpkz.jumpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The Employee entity.
 */
@Entity
@Table(name = "player")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * The firstname attribute.
     */
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @JsonIgnoreProperties(value = { "tasks", "employee", "statisticHistory" }, allowSetters = true)
    private Set<Statistic> statistics = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "statistics", "manager", "team", "statisticHistory" }, allowSetters = true)
    private Player manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "players", "tournaments" }, allowSetters = true)
    private Team team;

    @JsonIgnoreProperties(value = { "statistic", "department", "employee" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "employee")
    private StatisticHistory statisticHistory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Player id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Player firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Player lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Player email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Player phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Statistic> getStatistics() {
        return this.statistics;
    }

    public void setStatistics(Set<Statistic> statistics) {
        if (this.statistics != null) {
            this.statistics.forEach(i -> i.setEmployee(null));
        }
        if (statistics != null) {
            statistics.forEach(i -> i.setEmployee(this));
        }
        this.statistics = statistics;
    }

    public Player statistics(Set<Statistic> statistics) {
        this.setStatistics(statistics);
        return this;
    }

    public Player addStatistic(Statistic statistic) {
        this.statistics.add(statistic);
        statistic.setEmployee(this);
        return this;
    }

    public Player removeStatistic(Statistic statistic) {
        this.statistics.remove(statistic);
        statistic.setEmployee(null);
        return this;
    }

    public Player getManager() {
        return this.manager;
    }

    public void setManager(Player player) {
        this.manager = player;
    }

    public Player manager(Player player) {
        this.setManager(player);
        return this;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Player team(Team team) {
        this.setTeam(team);
        return this;
    }

    public StatisticHistory getStatisticHistory() {
        return this.statisticHistory;
    }

    public void setStatisticHistory(StatisticHistory statisticHistory) {
        if (this.statisticHistory != null) {
            this.statisticHistory.setEmployee(null);
        }
        if (statisticHistory != null) {
            statisticHistory.setEmployee(this);
        }
        this.statisticHistory = statisticHistory;
    }

    public Player statisticHistory(StatisticHistory statisticHistory) {
        this.setStatisticHistory(statisticHistory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        return getId() != null && getId().equals(((Player) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Player{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
