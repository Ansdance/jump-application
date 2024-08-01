package kz.jumpkz.jumpapp.domain;

import static kz.jumpkz.jumpapp.domain.PlayerTestSamples.*;
import static kz.jumpkz.jumpapp.domain.StatisticHistoryTestSamples.*;
import static kz.jumpkz.jumpapp.domain.StatisticTestSamples.*;
import static kz.jumpkz.jumpapp.domain.TournamentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import kz.jumpkz.jumpapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatisticHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatisticHistory.class);
        StatisticHistory statisticHistory1 = getStatisticHistorySample1();
        StatisticHistory statisticHistory2 = new StatisticHistory();
        assertThat(statisticHistory1).isNotEqualTo(statisticHistory2);

        statisticHistory2.setId(statisticHistory1.getId());
        assertThat(statisticHistory1).isEqualTo(statisticHistory2);

        statisticHistory2 = getStatisticHistorySample2();
        assertThat(statisticHistory1).isNotEqualTo(statisticHistory2);
    }

    @Test
    void statisticTest() {
        StatisticHistory statisticHistory = getStatisticHistoryRandomSampleGenerator();
        Statistic statisticBack = getStatisticRandomSampleGenerator();

        statisticHistory.setStatistic(statisticBack);
        assertThat(statisticHistory.getStatistic()).isEqualTo(statisticBack);

        statisticHistory.statistic(null);
        assertThat(statisticHistory.getStatistic()).isNull();
    }

    @Test
    void departmentTest() {
        StatisticHistory statisticHistory = getStatisticHistoryRandomSampleGenerator();
        Tournament tournamentBack = getTournamentRandomSampleGenerator();

        statisticHistory.setDepartment(tournamentBack);
        assertThat(statisticHistory.getDepartment()).isEqualTo(tournamentBack);

        statisticHistory.department(null);
        assertThat(statisticHistory.getDepartment()).isNull();
    }

    @Test
    void employeeTest() {
        StatisticHistory statisticHistory = getStatisticHistoryRandomSampleGenerator();
        Player playerBack = getPlayerRandomSampleGenerator();

        statisticHistory.setEmployee(playerBack);
        assertThat(statisticHistory.getEmployee()).isEqualTo(playerBack);

        statisticHistory.employee(null);
        assertThat(statisticHistory.getEmployee()).isNull();
    }
}
