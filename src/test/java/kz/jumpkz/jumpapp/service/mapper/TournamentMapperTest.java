package kz.jumpkz.jumpapp.service.mapper;

import static kz.jumpkz.jumpapp.domain.TournamentAsserts.*;
import static kz.jumpkz.jumpapp.domain.TournamentTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TournamentMapperTest {

    private TournamentMapper tournamentMapper;

    @BeforeEach
    void setUp() {
        tournamentMapper = new TournamentMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTournamentSample1();
        var actual = tournamentMapper.toEntity(tournamentMapper.toDto(expected));
        assertTournamentAllPropertiesEquals(expected, actual);
    }
}
