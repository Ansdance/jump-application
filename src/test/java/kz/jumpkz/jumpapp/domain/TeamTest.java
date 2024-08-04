package kz.jumpkz.jumpapp.domain;

import static kz.jumpkz.jumpapp.domain.PlayerTestSamples.*;
import static kz.jumpkz.jumpapp.domain.TeamTestSamples.*;
import static kz.jumpkz.jumpapp.domain.TournamentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import kz.jumpkz.jumpapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Team.class);
        Team team1 = getTeamSample1();
        Team team2 = new Team();
        assertThat(team1).isNotEqualTo(team2);

        team2.setId(team1.getId());
        assertThat(team1).isEqualTo(team2);

        team2 = getTeamSample2();
        assertThat(team1).isNotEqualTo(team2);
    }

    @Test
    void playerTest() {
        Team team = getTeamRandomSampleGenerator();
        Player playerBack = getPlayerRandomSampleGenerator();

        team.addPlayer(playerBack);
        assertThat(team.getPlayers()).containsOnly(playerBack);
        assertThat(playerBack.getTeam()).isEqualTo(team);

        team.removePlayer(playerBack);
        assertThat(team.getPlayers()).doesNotContain(playerBack);
        assertThat(playerBack.getTeam()).isNull();

        team.players(new HashSet<>(Set.of(playerBack)));
        assertThat(team.getPlayers()).containsOnly(playerBack);
        assertThat(playerBack.getTeam()).isEqualTo(team);

        team.setPlayers(new HashSet<>());
        assertThat(team.getPlayers()).doesNotContain(playerBack);
        assertThat(playerBack.getTeam()).isNull();
    }

    @Test
    void tournamentTest() {
        Team team = getTeamRandomSampleGenerator();
        Tournament tournamentBack = getTournamentRandomSampleGenerator();

        team.addTournament(tournamentBack);
        assertThat(team.getTournaments()).containsOnly(tournamentBack);
        assertThat(tournamentBack.getTeams()).containsOnly(team);

        team.removeTournament(tournamentBack);
        assertThat(team.getTournaments()).doesNotContain(tournamentBack);
        assertThat(tournamentBack.getTeams()).doesNotContain(team);

        team.tournaments(new HashSet<>(Set.of(tournamentBack)));
        assertThat(team.getTournaments()).containsOnly(tournamentBack);
        assertThat(tournamentBack.getTeams()).containsOnly(team);

        team.setTournaments(new HashSet<>());
        assertThat(team.getTournaments()).doesNotContain(tournamentBack);
        assertThat(tournamentBack.getTeams()).doesNotContain(team);
    }
}
