package kz.jumpkz.jumpapp.domain;

import static kz.jumpkz.jumpapp.domain.PlayerTestSamples.*;
import static kz.jumpkz.jumpapp.domain.StatisticHistoryTestSamples.*;
import static kz.jumpkz.jumpapp.domain.StatisticTestSamples.*;
import static kz.jumpkz.jumpapp.domain.TaskTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import kz.jumpkz.jumpapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatisticTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Statistic.class);
        Statistic statistic1 = getStatisticSample1();
        Statistic statistic2 = new Statistic();
        assertThat(statistic1).isNotEqualTo(statistic2);

        statistic2.setId(statistic1.getId());
        assertThat(statistic1).isEqualTo(statistic2);

        statistic2 = getStatisticSample2();
        assertThat(statistic1).isNotEqualTo(statistic2);
    }

    @Test
    void taskTest() {
        Statistic statistic = getStatisticRandomSampleGenerator();
        Task taskBack = getTaskRandomSampleGenerator();

        statistic.addTask(taskBack);
        assertThat(statistic.getTasks()).containsOnly(taskBack);

        statistic.removeTask(taskBack);
        assertThat(statistic.getTasks()).doesNotContain(taskBack);

        statistic.tasks(new HashSet<>(Set.of(taskBack)));
        assertThat(statistic.getTasks()).containsOnly(taskBack);

        statistic.setTasks(new HashSet<>());
        assertThat(statistic.getTasks()).doesNotContain(taskBack);
    }

    @Test
    void employeeTest() {
        Statistic statistic = getStatisticRandomSampleGenerator();
        Player playerBack = getPlayerRandomSampleGenerator();

        statistic.setEmployee(playerBack);
        assertThat(statistic.getEmployee()).isEqualTo(playerBack);

        statistic.employee(null);
        assertThat(statistic.getEmployee()).isNull();
    }

    @Test
    void statisticHistoryTest() {
        Statistic statistic = getStatisticRandomSampleGenerator();
        StatisticHistory statisticHistoryBack = getStatisticHistoryRandomSampleGenerator();

        statistic.setStatisticHistory(statisticHistoryBack);
        assertThat(statistic.getStatisticHistory()).isEqualTo(statisticHistoryBack);
        assertThat(statisticHistoryBack.getStatistic()).isEqualTo(statistic);

        statistic.statisticHistory(null);
        assertThat(statistic.getStatisticHistory()).isNull();
        assertThat(statisticHistoryBack.getStatistic()).isNull();
    }
}
