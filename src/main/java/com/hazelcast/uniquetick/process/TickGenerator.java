package com.hazelcast.uniquetick.process;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
@Component
public class TickGenerator {

    private Long tick = Long.valueOf(0);
    private final Lock lock = new ReentrantLock();

    public long getUniqueTick () {
        long returnValue = -1;
        try {
            if (lock.tryLock(1, TimeUnit.MILLISECONDS)) {
                try {
                    returnValue = tick.longValue();
                    tick++;
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        //return the actual value from the counter or -1 if it wasn't possible to get the lock (interrupted or exceeded wait time)
        return returnValue;
    }
}
