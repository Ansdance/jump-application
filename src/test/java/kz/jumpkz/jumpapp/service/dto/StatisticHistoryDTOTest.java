package kz.jumpkz.jumpapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import kz.jumpkz.jumpapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatisticHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatisticHistoryDTO.class);
        StatisticHistoryDTO statisticHistoryDTO1 = new StatisticHistoryDTO();
        statisticHistoryDTO1.setId(1L);
        StatisticHistoryDTO statisticHistoryDTO2 = new StatisticHistoryDTO();
        assertThat(statisticHistoryDTO1).isNotEqualTo(statisticHistoryDTO2);
        statisticHistoryDTO2.setId(statisticHistoryDTO1.getId());
        assertThat(statisticHistoryDTO1).isEqualTo(statisticHistoryDTO2);
        statisticHistoryDTO2.setId(2L);
        assertThat(statisticHistoryDTO1).isNotEqualTo(statisticHistoryDTO2);
        statisticHistoryDTO1.setId(null);
        assertThat(statisticHistoryDTO1).isNotEqualTo(statisticHistoryDTO2);
    }
}
