package com.bonepl.chromaleague.rest.http;

import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class BlockingResponseInterceptor implements HttpResponseInterceptor {
    @Override
    public void process(HttpResponse response, HttpContext context) throws IOException {
        if (response.getEntity().getContentLength() <= 2) {
            throw new IOException("Content too small, retrying");
        }
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new IOException("HTTP status not 200, retrying");
        }
    }
}
