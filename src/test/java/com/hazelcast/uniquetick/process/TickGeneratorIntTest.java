package com.hazelcast.uniquetick.process;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TickGeneratorIntTest {

    @Test
    public void endpointTest () throws IOException {
        // Given
        HttpUriRequest request = new HttpGet(
                "http://ec2-52-56-223-81.eu-west-2.compute.amazonaws.com:8080/getUniqueTick" );

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute( request );

        System.out.println(response.toString());
    }
}
