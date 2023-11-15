package com.hazelcast.uniquetick.process;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class WaitingTickValueWorker implements Runnable{

    private final CountDownLatch threadReadyToWorkCounter;
    private final CountDownLatch callerBlocker;
    private final CountDownLatch threadsCompleteCounter;
    final TickGenerator tickGenerator;

    private final List<Long> result;

    WaitingTickValueWorker (final TickGenerator tickGenerator, final CountDownLatch threadReadyToWorkCounter, final CountDownLatch callerBlocker, final CountDownLatch threadsCompleteCounter, final List<Long> result) {
        this.tickGenerator = tickGenerator;
        this.threadReadyToWorkCounter = threadReadyToWorkCounter;
        this.callerBlocker = callerBlocker;
        this.threadsCompleteCounter = threadsCompleteCounter;
        this.result = result;
    }

    @Override
    public void run() {
        threadReadyToWorkCounter.countDown();
        try {
            callerBlocker.await();
            result.add(tickGenerator.getUniqueTick());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            threadsCompleteCounter.countDown();
        }
    }
}
