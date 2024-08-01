package kz.jumpkz.jumpapp.domain;

import static kz.jumpkz.jumpapp.domain.LocationTestSamples.*;
import static kz.jumpkz.jumpapp.domain.PlayerTestSamples.*;
import static kz.jumpkz.jumpapp.domain.StatisticHistoryTestSamples.*;
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
    void playerTest() {
        Tournament tournament = getTournamentRandomSampleGenerator();
        Player playerBack = getPlayerRandomSampleGenerator();

        tournament.addPlayer(playerBack);
        assertThat(tournament.getPlayers()).containsOnly(playerBack);
        assertThat(playerBack.getTournament()).isEqualTo(tournament);

        tournament.removePlayer(playerBack);
        assertThat(tournament.getPlayers()).doesNotContain(playerBack);
        assertThat(playerBack.getTournament()).isNull();

        tournament.players(new HashSet<>(Set.of(playerBack)));
        assertThat(tournament.getPlayers()).containsOnly(playerBack);
        assertThat(playerBack.getTournament()).isEqualTo(tournament);

        tournament.setPlayers(new HashSet<>());
        assertThat(tournament.getPlayers()).doesNotContain(playerBack);
        assertThat(playerBack.getTournament()).isNull();
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
