package com.hazelcast.uniquetick.process;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TickGeneratorTest {

    @Test
    void getUniqueTickValues_SingleThreaded() {

        //assemble
        final TickGenerator tickGenerator = new TickGenerator();
        long currentTick = 0;
        final long expectedFinalTickValue = 49;
        final LongStream stream = LongStream.range(0, expectedFinalTickValue + 1);

        //act
        currentTick = (stream.map(x ->
               tickGenerator.getUniqueTick()
        ).max()).getAsLong();

        //assert
        assertEquals(expectedFinalTickValue, currentTick);
    }

    @Test
    void getUniqueTickValues_RunConcurrentlyNonWaitingStart() throws InterruptedException {

        //assemble
        final int expectedTotalUniqueTicks= 100;
        final CountDownLatch latch = new CountDownLatch(expectedTotalUniqueTicks);
        final List<Long> result = new ArrayList<>();
        final TickGenerator tickGenerator = new TickGenerator();

        //act
        final List<Thread> workers = Stream
                .generate(() -> new Thread(new NonWaitingTickValueWorker(tickGenerator, latch, result)))
                .limit(expectedTotalUniqueTicks)
                .collect(Collectors.toList());

        workers.forEach(Thread::start);
        latch.await();

        //assert
        assertResult(result, expectedTotalUniqueTicks);
    }
    @Test
    public void getUniqueTickValues_RunConcurrentlyWaitingStart()
            throws InterruptedException {

        //assemble
        final int expectedTotalUniqueTicks= 100;
        final List<Long> result = new ArrayList<>();
        final CountDownLatch readyThreadCounter = new CountDownLatch(expectedTotalUniqueTicks);
        final CountDownLatch callerBlocker = new CountDownLatch(1);
        final CountDownLatch threadsCompleteCounter = new CountDownLatch(expectedTotalUniqueTicks);
        final TickGenerator tickGenerator = new TickGenerator();

        //act
        List<Thread> workers = Stream
                .generate(() -> new Thread(new WaitingTickValueWorker(
                        tickGenerator, readyThreadCounter, callerBlocker, threadsCompleteCounter, result)))
                .limit(expectedTotalUniqueTicks)
                .collect(Collectors.toList());

        workers.forEach(Thread::start);
        readyThreadCounter.await();
        callerBlocker.countDown();
        threadsCompleteCounter.await();

        //assert
        assertResult(result, expectedTotalUniqueTicks);
    }
    private void assertResult(List<Long> result, int expectedTotalUniqueTicks) {
        assertEquals(result.size(), expectedTotalUniqueTicks, "Unexpected result size: " + result.size());
        List<Long> expectedValuesInResult = LongStream.rangeClosed(0, expectedTotalUniqueTicks - 1).boxed().collect(Collectors.toList());
        assertTrue(result.containsAll(expectedValuesInResult) && expectedValuesInResult.containsAll(result));
    }
}
