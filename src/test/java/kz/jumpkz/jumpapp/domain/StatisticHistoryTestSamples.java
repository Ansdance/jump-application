package kz.jumpkz.jumpapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class StatisticHistoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static StatisticHistory getStatisticHistorySample1() {
        return new StatisticHistory().id(1L);
    }

    public static StatisticHistory getStatisticHistorySample2() {
        return new StatisticHistory().id(2L);
    }

    public static StatisticHistory getStatisticHistoryRandomSampleGenerator() {
        return new StatisticHistory().id(longCount.incrementAndGet());
    }
}
