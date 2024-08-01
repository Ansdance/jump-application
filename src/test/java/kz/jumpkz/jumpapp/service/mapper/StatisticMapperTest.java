package kz.jumpkz.jumpapp.service.mapper;

import static kz.jumpkz.jumpapp.domain.StatisticAsserts.*;
import static kz.jumpkz.jumpapp.domain.StatisticTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatisticMapperTest {

    private StatisticMapper statisticMapper;

    @BeforeEach
    void setUp() {
        statisticMapper = new StatisticMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getStatisticSample1();
        var actual = statisticMapper.toEntity(statisticMapper.toDto(expected));
        assertStatisticAllPropertiesEquals(expected, actual);
    }
}
