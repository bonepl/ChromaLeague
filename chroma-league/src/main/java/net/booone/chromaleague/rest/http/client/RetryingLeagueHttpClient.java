package net.booone.chromaleague.rest.http.client;

import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class RetryingLeagueHttpClient extends AbstractLeagueHttpClient {
    private static final Logger LOGGER = Logger.getLogger(RetryingLeagueHttpClient.class.getName());

    public <T> T getResponse(final String url, ResponseHandler<T> responseHandler) {
        try {
            return super.execute(url, responseHandler);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "This shouldn't happen in retrying league http client", e);
            throw new IllegalStateException(e);
        }
    }

    public CloseableHttpClient createLeagueHttpClient() {
        return commonHttpClientBuilder()
                .addInterceptorFirst(new RetryOnNon200StatusInterceptor())
                .addInterceptorLast(new RetryOnContentTooSmallInterceptor())
                .setRetryHandler(new StandardHttpRequestRetryHandler(10, true))
                .build();
    }
}
