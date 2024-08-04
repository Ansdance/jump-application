package kz.jumpkz.jumpapp.domain;

import static kz.jumpkz.jumpapp.domain.LocationTestSamples.*;
import static kz.jumpkz.jumpapp.domain.StatisticHistoryTestSamples.*;
import static kz.jumpkz.jumpapp.domain.TeamTestSamples.*;
import static kz.jumpkz.jumpapp.domain.TournamentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import kz.jumpkz.jumpapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TournamentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tournament.class);
        Tournament tournament1 = getTournamentSample1();
        Tournament tournament2 = new Tournament();
        assertThat(tournament1).isNotEqualTo(tournament2);

        tournament2.setId(tournament1.getId());
        assertThat(tournament1).isEqualTo(tournament2);

        tournament2 = getTournamentSample2();
        assertThat(tournament1).isNotEqualTo(tournament2);
    }

    @Test
    void locationTest() {
        Tournament tournament = getTournamentRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        tournament.setLocation(locationBack);
        assertThat(tournament.getLocation()).isEqualTo(locationBack);

        tournament.location(null);
        assertThat(tournament.getLocation()).isNull();
    }

    @Test
    void teamTest() {
        Tournament tournament = getTournamentRandomSampleGenerator();
        Team teamBack = getTeamRandomSampleGenerator();

        tournament.addTeam(teamBack);
        assertThat(tournament.getTeams()).containsOnly(teamBack);

        tournament.removeTeam(teamBack);
        assertThat(tournament.getTeams()).doesNotContain(teamBack);

        tournament.teams(new HashSet<>(Set.of(teamBack)));
        assertThat(tournament.getTeams()).containsOnly(teamBack);

        tournament.setTeams(new HashSet<>());
        assertThat(tournament.getTeams()).doesNotContain(teamBack);
    }

    @Test
    void statisticHistoryTest() {
        Tournament tournament = getTournamentRandomSampleGenerator();
        StatisticHistory statisticHistoryBack = getStatisticHistoryRandomSampleGenerator();

        tournament.setStatisticHistory(statisticHistoryBack);
        assertThat(tournament.getStatisticHistory()).isEqualTo(statisticHistoryBack);
        assertThat(statisticHistoryBack.getDepartment()).isEqualTo(tournament);

        tournament.statisticHistory(null);
        assertThat(tournament.getStatisticHistory()).isNull();
        assertThat(statisticHistoryBack.getDepartment()).isNull();
    }
}
