package kz.jumpkz.jumpapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StatisticTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Statistic getStatisticSample1() {
        return new Statistic().id(1L).statisticTitle("statisticTitle1");
    }

    public static Statistic getStatisticSample2() {
        return new Statistic().id(2L).statisticTitle("statisticTitle2");
    }

    public static Statistic getStatisticRandomSampleGenerator() {
        return new Statistic().id(longCount.incrementAndGet()).statisticTitle(UUID.randomUUID().toString());
    }
}
