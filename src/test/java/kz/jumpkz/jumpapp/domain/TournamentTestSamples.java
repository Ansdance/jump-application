package kz.jumpkz.jumpapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TournamentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Tournament getTournamentSample1() {
        return new Tournament().id(1L).tournamentName("tournamentName1");
    }

    public static Tournament getTournamentSample2() {
        return new Tournament().id(2L).tournamentName("tournamentName2");
    }

    public static Tournament getTournamentRandomSampleGenerator() {
        return new Tournament().id(longCount.incrementAndGet()).tournamentName(UUID.randomUUID().toString());
    }
}
