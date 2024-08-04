package kz.jumpkz.jumpapp.domain;

import static kz.jumpkz.jumpapp.domain.StatisticTestSamples.*;
import static kz.jumpkz.jumpapp.domain.TaskTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import kz.jumpkz.jumpapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Task.class);
        Task task1 = getTaskSample1();
        Task task2 = new Task();
        assertThat(task1).isNotEqualTo(task2);

        task2.setId(task1.getId());
        assertThat(task1).isEqualTo(task2);

        task2 = getTaskSample2();
        assertThat(task1).isNotEqualTo(task2);
    }

    @Test
    void statisticTest() {
        Task task = getTaskRandomSampleGenerator();
        Statistic statisticBack = getStatisticRandomSampleGenerator();

        task.addStatistic(statisticBack);
        assertThat(task.getStatistics()).containsOnly(statisticBack);
        assertThat(statisticBack.getTasks()).containsOnly(task);

        task.removeStatistic(statisticBack);
        assertThat(task.getStatistics()).doesNotContain(statisticBack);
        assertThat(statisticBack.getTasks()).doesNotContain(task);

        task.statistics(new HashSet<>(Set.of(statisticBack)));
        assertThat(task.getStatistics()).containsOnly(statisticBack);
        assertThat(statisticBack.getTasks()).containsOnly(task);

        task.setStatistics(new HashSet<>());
        assertThat(task.getStatistics()).doesNotContain(statisticBack);
        assertThat(statisticBack.getTasks()).doesNotContain(task);
    }
}
