package com.hazelcast.uniquetick.process;

import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
@Component
public class TickGenerator {

    private Long tick = Long.valueOf(0);
    private final Lock lock = new ReentrantLock();

    public long getUniqueTick ()  {
        lock.lock();
        try {
            long value = tick.longValue();
            tick++;
            return value;
        } finally {
            lock.unlock();
        }
    }
}
