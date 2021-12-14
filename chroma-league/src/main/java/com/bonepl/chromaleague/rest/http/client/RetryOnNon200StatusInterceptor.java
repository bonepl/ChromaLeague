package com.bonepl.chromaleague.rest.http.client;

import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class RetryOnNon200StatusInterceptor implements HttpResponseInterceptor {
    @Override
    public void process(HttpResponse response, HttpContext context) throws IOException {
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new IOException("HTTP status not 200, retrying");
        }
    }
}
