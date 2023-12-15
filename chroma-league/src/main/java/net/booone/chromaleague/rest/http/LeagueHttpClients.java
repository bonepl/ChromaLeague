package net.booone.chromaleague.rest.http;

import net.booone.chromaleague.rest.http.client.NonBlockingLeagueHttpClient;
import net.booone.chromaleague.rest.http.client.RetryingLeagueHttpClient;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

import java.util.Optional;

public final class LeagueHttpClients {
    private static NonBlockingLeagueHttpClient nonBlockingLeagueHttpClient = new NonBlockingLeagueHttpClient();
    private static RetryingLeagueHttpClient retryingLeagueHttpClient = new RetryingLeagueHttpClient();

    private LeagueHttpClients() {
    }

    public static <T> Optional<T> getNonBlockingResponse(final String url, HttpClientResponseHandler<Optional<T>> responseHandler) {
        return getNonBlockingLeagueHttpClient().getResponse(url, responseHandler);
    }

    public static <T> T getRetryingResponse(final String url, HttpClientResponseHandler<T> responseHandler) {
        return getRetryingLeagueHttpClient().getResponse(url, responseHandler);
    }

    static NonBlockingLeagueHttpClient getNonBlockingLeagueHttpClient() {
        return nonBlockingLeagueHttpClient;
    }

    static RetryingLeagueHttpClient getRetryingLeagueHttpClient() {
        return retryingLeagueHttpClient;
    }

    //TEST ONLY
    static void setNonBlockingLeagueHttpClient(NonBlockingLeagueHttpClient nonBlockingLeagueHttpClient) {
        LeagueHttpClients.nonBlockingLeagueHttpClient = nonBlockingLeagueHttpClient;
    }

    //TEST ONLY
    static void setRetryingLeagueHttpClient(RetryingLeagueHttpClient retryingLeagueHttpClient) {
        LeagueHttpClients.retryingLeagueHttpClient = retryingLeagueHttpClient;
    }
}
