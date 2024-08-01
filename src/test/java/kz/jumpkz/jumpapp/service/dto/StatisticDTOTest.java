package kz.jumpkz.jumpapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import kz.jumpkz.jumpapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatisticDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatisticDTO.class);
        StatisticDTO statisticDTO1 = new StatisticDTO();
        statisticDTO1.setId(1L);
        StatisticDTO statisticDTO2 = new StatisticDTO();
        assertThat(statisticDTO1).isNotEqualTo(statisticDTO2);
        statisticDTO2.setId(statisticDTO1.getId());
        assertThat(statisticDTO1).isEqualTo(statisticDTO2);
        statisticDTO2.setId(2L);
        assertThat(statisticDTO1).isNotEqualTo(statisticDTO2);
        statisticDTO1.setId(null);
        assertThat(statisticDTO1).isNotEqualTo(statisticDTO2);
    }
}
