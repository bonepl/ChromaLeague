package net.booone.chromaleague.rest.http.client;

import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpResponseInterceptor;
import org.apache.hc.core5.http.protocol.HttpContext;

import java.io.IOException;

public class RetryOnContentTooSmallInterceptor implements HttpResponseInterceptor {
    @Override
    public void process(HttpResponse response, EntityDetails entity, HttpContext context) throws IOException {
        if (entity.getContentLength() <= 2) {
            throw new IOException("Content too small, retrying");
        }
    }
}
