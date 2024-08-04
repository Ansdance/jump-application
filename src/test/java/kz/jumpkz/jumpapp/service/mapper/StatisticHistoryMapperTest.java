package kz.jumpkz.jumpapp.service.mapper;

import static kz.jumpkz.jumpapp.domain.StatisticHistoryAsserts.*;
import static kz.jumpkz.jumpapp.domain.StatisticHistoryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatisticHistoryMapperTest {

    private StatisticHistoryMapper statisticHistoryMapper;

    @BeforeEach
    void setUp() {
        statisticHistoryMapper = new StatisticHistoryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getStatisticHistorySample1();
        var actual = statisticHistoryMapper.toEntity(statisticHistoryMapper.toDto(expected));
        assertStatisticHistoryAllPropertiesEquals(expected, actual);
    }
}
