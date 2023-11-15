package com.hazelcast.uniquetick.process;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class NonWaitingTickValueWorker implements Runnable{

    private final CountDownLatch latch;
    private final List<Long> result;
    final TickGenerator tickGenerator;

    NonWaitingTickValueWorker(final TickGenerator tickGenerator, final CountDownLatch latch, final List<Long> result) {
        this.tickGenerator = tickGenerator;
        this.latch = latch;
        this.result = result;
    }

    @Override
    public void run() {
        result.add(tickGenerator.getUniqueTick());
        latch.countDown();
    }
}
