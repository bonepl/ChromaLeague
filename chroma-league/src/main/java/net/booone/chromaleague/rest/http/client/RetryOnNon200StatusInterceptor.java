package net.booone.chromaleague.rest.http.client;

import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpResponseInterceptor;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.protocol.HttpContext;

import java.io.IOException;

public class RetryOnNon200StatusInterceptor implements HttpResponseInterceptor {

    @Override
    public void process(HttpResponse response, EntityDetails entity, HttpContext context) throws IOException {
        if (response.getCode() != HttpStatus.SC_OK) {
            throw new IOException("HTTP status not 200, retrying");
        }
    }
}
