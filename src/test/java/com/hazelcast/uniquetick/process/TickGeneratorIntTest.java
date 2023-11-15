package com.hazelcast.uniquetick.process;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TickGeneratorIntTest {

    @Test
    public void endpointTest () throws Exception {

        final int expectedTotalUniqueTicks= 10;
        final CountDownLatch latch = new CountDownLatch(expectedTotalUniqueTicks);
        final List<Long> result = new ArrayList<>();
        int successCode = 200;

        // assemble
        Runnable runnable = () -> {
            try {
                HttpUriRequest request = new HttpGet(
                        "http://ec2-52-56-233-81.eu-west-2.compute.amazonaws.com:8080/getUniqueTick");
                HttpResponse response = HttpClientBuilder.create().build().execute(request);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == successCode) {
                    Long tickValue = Long.parseLong(EntityUtils.toString(response.getEntity()));
                    result.add(tickValue);
                }
            } catch (IOException ioe) {
              //handle better
              ioe.printStackTrace();
            }
        };

        // act
        final List<Thread> workers = Stream
                .generate(() -> new Thread(runnable))
                .limit(100)
                .collect(Collectors.toList());

        workers.forEach(Thread::start);
        latch.await();

        //assert
        System.out.println(result);
    }
}
