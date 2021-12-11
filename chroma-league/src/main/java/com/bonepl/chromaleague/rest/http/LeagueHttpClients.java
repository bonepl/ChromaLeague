package com.bonepl.chromaleague.rest.http;

import com.bonepl.chromaleague.rest.http.client.NonBlockingLeagueHttpClient;
import com.bonepl.chromaleague.rest.http.client.RetryingLeagueHttpClient;
import org.apache.http.client.ResponseHandler;

import java.util.Optional;

public final class LeagueHttpClients {
    private static NonBlockingLeagueHttpClient nonBlockingLeagueHttpClient = new NonBlockingLeagueHttpClient();
    private static RetryingLeagueHttpClient retryingLeagueHttpClient = new RetryingLeagueHttpClient();

    private LeagueHttpClients() {
    }

    public static <T> Optional<T> getNonBlockingResponse(final String url, ResponseHandler<Optional<T>> responseHandler) {
        return getNonBlockingLeagueHttpClient().getResponse(url, responseHandler);
    }

    public static <T> T getBlockingResponse(final String url, ResponseHandler<T> responseHandler) {
        return getBlockingLeagueHttpClient().getResponse(url, responseHandler);
    }

    static NonBlockingLeagueHttpClient getNonBlockingLeagueHttpClient() {
        return nonBlockingLeagueHttpClient;
    }

    static RetryingLeagueHttpClient getBlockingLeagueHttpClient() {
        return retryingLeagueHttpClient;
    }

    //TEST ONLY
    static void setNonBlockingLeagueHttpClient(NonBlockingLeagueHttpClient nonBlockingLeagueHttpClient) {
        LeagueHttpClients.nonBlockingLeagueHttpClient = nonBlockingLeagueHttpClient;
    }

    //TEST ONLY
    static void setBlockingLeagueHttpClient(RetryingLeagueHttpClient retryingLeagueHttpClient) {
        LeagueHttpClients.retryingLeagueHttpClient = retryingLeagueHttpClient;
    }
}
