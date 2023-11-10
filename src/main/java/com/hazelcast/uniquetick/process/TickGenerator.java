package com.hazelcast.uniquetick.process;

import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;
@Component
public class TickGenerator {

    private Long tick = Long.valueOf(0);
    private final ReentrantLock lock = new ReentrantLock();

    public long getUniqueTick ()  {
        lock.lock();
        try {
            Thread.sleep(250);
            long value = tick.longValue();
            tick++;
            return value;
        } catch (InterruptedException i) {
            throw new RuntimeException(i);
        } finally {
            lock.unlock();
        }
    }
}
