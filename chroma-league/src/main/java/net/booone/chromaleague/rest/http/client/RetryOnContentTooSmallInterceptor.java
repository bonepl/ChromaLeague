package net.booone.chromaleague.rest.http.client;

import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class RetryOnContentTooSmallInterceptor implements HttpResponseInterceptor {
    @Override
    public void process(HttpResponse response, HttpContext context) throws IOException {
        if (response.getEntity().getContentLength() <= 2) {
            throw new IOException("Content too small, retrying");
        }
    }
}
