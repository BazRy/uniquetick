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
        threadReadyToWorkCounter.countDown(); //countdown that this worker is ready
        try {
            callerBlocker.await(); //but we cannot run this method as we are blocked,  until a countdown has occurred
            result.add(tickGenerator.getUniqueTick());  //all threads ready so above latch is open,  now let thread run
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            threadsCompleteCounter.countDown(); //countdown this latch
        }
    }
}
