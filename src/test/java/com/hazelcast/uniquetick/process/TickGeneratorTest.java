package com.hazelcast.uniquetick.process;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TickGeneratorTest {

    @Test
    void getUniqueTickValuesBasicTest() {
        //assemble
        final TickGenerator tickGenerator = new TickGenerator();
        long tick = 0;
        final long expectedTick = 4;

        //act
        for (int i = 0; i <= expectedTick; i++) {
            tick = tickGenerator.getUniqueTick();
            System.out.println("Tick: " + tick);
        }

        //assert
        assertEquals(expectedTick, tick);
    }

    @Test
    void getUniqueTickValuesMultiThreadedTest() throws Exception {
        //assemble
        final CountDownLatch latch = new CountDownLatch(100);
        final TickGenerator tickGenerator = new TickGenerator();
        final ExecutorService service = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            service.submit(new Processor(latch, tickGenerator));
        }
        latch.await();
        System.out.println("Completed");
    }

    class Processor implements Runnable {

        private final CountDownLatch latch;
        private final TickGenerator tickGenerator;

        Processor (CountDownLatch latch, TickGenerator tickGenerator) {
            this.latch = latch;
            this.tickGenerator = tickGenerator;
        }
        long tick = 0;
        @Override
        public void run() {
            tick = tickGenerator.getUniqueTick();
            System.out.println("Tick: " + tick);
            latch.countDown();
        }
    }
}
