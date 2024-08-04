package kz.jumpkz.jumpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tournament.
 */
@Entity
@Table(name = "tournament")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tournament implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "tournament_name", nullable = false)
    private String tournamentName;

    @JsonIgnoreProperties(value = { "country", "tournament" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Location location;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_tournament__team",
        joinColumns = @JoinColumn(name = "tournament_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    @JsonIgnoreProperties(value = { "players", "tournaments" }, allowSetters = true)
    private Set<Team> teams = new HashSet<>();

    @JsonIgnoreProperties(value = { "statistic", "department", "employee" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "department")
    private StatisticHistory statisticHistory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tournament id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTournamentName() {
        return this.tournamentName;
    }

    public Tournament tournamentName(String tournamentName) {
        this.setTournamentName(tournamentName);
        return this;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Tournament location(Location location) {
        this.setLocation(location);
        return this;
    }

    public Set<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Tournament teams(Set<Team> teams) {
        this.setTeams(teams);
        return this;
    }

    public Tournament addTeam(Team team) {
        this.teams.add(team);
        return this;
    }

    public Tournament removeTeam(Team team) {
        this.teams.remove(team);
        return this;
    }

    public StatisticHistory getStatisticHistory() {
        return this.statisticHistory;
    }

    public void setStatisticHistory(StatisticHistory statisticHistory) {
        if (this.statisticHistory != null) {
            this.statisticHistory.setDepartment(null);
        }
        if (statisticHistory != null) {
            statisticHistory.setDepartment(this);
        }
        this.statisticHistory = statisticHistory;
    }

    public Tournament statisticHistory(StatisticHistory statisticHistory) {
        this.setStatisticHistory(statisticHistory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tournament)) {
            return false;
        }
        return getId() != null && getId().equals(((Tournament) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tournament{" +
            "id=" + getId() +
            ", tournamentName='" + getTournamentName() + "'" +
            "}";
    }
}
