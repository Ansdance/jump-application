package kz.jumpkz.jumpapp.domain;

import static kz.jumpkz.jumpapp.domain.PlayerTestSamples.*;
import static kz.jumpkz.jumpapp.domain.PlayerTestSamples.*;
import static kz.jumpkz.jumpapp.domain.StatisticHistoryTestSamples.*;
import static kz.jumpkz.jumpapp.domain.StatisticTestSamples.*;
import static kz.jumpkz.jumpapp.domain.TeamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import kz.jumpkz.jumpapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlayerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Player.class);
        Player player1 = getPlayerSample1();
        Player player2 = new Player();
        assertThat(player1).isNotEqualTo(player2);

        player2.setId(player1.getId());
        assertThat(player1).isEqualTo(player2);

        player2 = getPlayerSample2();
        assertThat(player1).isNotEqualTo(player2);
    }

    @Test
    void statisticTest() {
        Player player = getPlayerRandomSampleGenerator();
        Statistic statisticBack = getStatisticRandomSampleGenerator();

        player.addStatistic(statisticBack);
        assertThat(player.getStatistics()).containsOnly(statisticBack);
        assertThat(statisticBack.getEmployee()).isEqualTo(player);

        player.removeStatistic(statisticBack);
        assertThat(player.getStatistics()).doesNotContain(statisticBack);
        assertThat(statisticBack.getEmployee()).isNull();

        player.statistics(new HashSet<>(Set.of(statisticBack)));
        assertThat(player.getStatistics()).containsOnly(statisticBack);
        assertThat(statisticBack.getEmployee()).isEqualTo(player);

        player.setStatistics(new HashSet<>());
        assertThat(player.getStatistics()).doesNotContain(statisticBack);
        assertThat(statisticBack.getEmployee()).isNull();
    }

    @Test
    void managerTest() {
        Player player = getPlayerRandomSampleGenerator();
        Player playerBack = getPlayerRandomSampleGenerator();

        player.setManager(playerBack);
        assertThat(player.getManager()).isEqualTo(playerBack);

        player.manager(null);
        assertThat(player.getManager()).isNull();
    }

    @Test
    void teamTest() {
        Player player = getPlayerRandomSampleGenerator();
        Team teamBack = getTeamRandomSampleGenerator();

        player.setTeam(teamBack);
        assertThat(player.getTeam()).isEqualTo(teamBack);

        player.team(null);
        assertThat(player.getTeam()).isNull();
    }

    @Test
    void statisticHistoryTest() {
        Player player = getPlayerRandomSampleGenerator();
        StatisticHistory statisticHistoryBack = getStatisticHistoryRandomSampleGenerator();

        player.setStatisticHistory(statisticHistoryBack);
        assertThat(player.getStatisticHistory()).isEqualTo(statisticHistoryBack);
        assertThat(statisticHistoryBack.getEmployee()).isEqualTo(player);

        player.statisticHistory(null);
        assertThat(player.getStatisticHistory()).isNull();
        assertThat(statisticHistoryBack.getEmployee()).isNull();
    }
}
