package kz.jumpkz.jumpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "team_name")
    private String teamName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    @JsonIgnoreProperties(value = { "statistics", "manager", "team", "statisticHistory" }, allowSetters = true)
    private Set<Player> players = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "teams")
    @JsonIgnoreProperties(value = { "location", "teams", "statisticHistory" }, allowSetters = true)
    private Set<Tournament> tournaments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Team id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public Team teamName(String teamName) {
        this.setTeamName(teamName);
        return this;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Set<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(Set<Player> players) {
        if (this.players != null) {
            this.players.forEach(i -> i.setTeam(null));
        }
        if (players != null) {
            players.forEach(i -> i.setTeam(this));
        }
        this.players = players;
    }

    public Team players(Set<Player> players) {
        this.setPlayers(players);
        return this;
    }

    public Team addPlayer(Player player) {
        this.players.add(player);
        player.setTeam(this);
        return this;
    }

    public Team removePlayer(Player player) {
        this.players.remove(player);
        player.setTeam(null);
        return this;
    }

    public Set<Tournament> getTournaments() {
        return this.tournaments;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        if (this.tournaments != null) {
            this.tournaments.forEach(i -> i.removeTeam(this));
        }
        if (tournaments != null) {
            tournaments.forEach(i -> i.addTeam(this));
        }
        this.tournaments = tournaments;
    }

    public Team tournaments(Set<Tournament> tournaments) {
        this.setTournaments(tournaments);
        return this;
    }

    public Team addTournament(Tournament tournament) {
        this.tournaments.add(tournament);
        tournament.getTeams().add(this);
        return this;
    }

    public Team removeTournament(Tournament tournament) {
        this.tournaments.remove(tournament);
        tournament.getTeams().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }
        return getId() != null && getId().equals(((Team) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", teamName='" + getTeamName() + "'" +
            "}";
    }
}
