package com.bonepl.chromaleague.rest.http;

import com.bonepl.chromaleague.rest.http.client.BlockingLeagueHttpClient;
import com.bonepl.chromaleague.rest.http.client.NonBlockingLeagueHttpClient;
import org.apache.http.client.ResponseHandler;

import java.util.Optional;

public final class LeagueHttpClients {
    private static NonBlockingLeagueHttpClient nonBlockingLeagueHttpClient = new NonBlockingLeagueHttpClient();
    private static BlockingLeagueHttpClient blockingLeagueHttpClient = new BlockingLeagueHttpClient();

    private LeagueHttpClients() {
    }

    public static <T> Optional<T> getNonBlockingResponse(final String url, ResponseHandler<Optional<T>> responseHandler) {
        return getNonBlockingLeagueHttpClient().getNonBlockingResponse(url, responseHandler);
    }

    public static <T> T getBlockingResponse(final String url, ResponseHandler<T> responseHandler) {
        return getBlockingLeagueHttpClient().getBlockingResponse(url, responseHandler);
    }

    static NonBlockingLeagueHttpClient getNonBlockingLeagueHttpClient() {
        return nonBlockingLeagueHttpClient;
    }

    static BlockingLeagueHttpClient getBlockingLeagueHttpClient() {
        return blockingLeagueHttpClient;
    }

    //TEST ONLY
    static void setNonBlockingLeagueHttpClient(NonBlockingLeagueHttpClient nonBlockingLeagueHttpClient) {
        LeagueHttpClients.nonBlockingLeagueHttpClient = nonBlockingLeagueHttpClient;
    }

    //TEST ONLY
    static void setBlockingLeagueHttpClient(BlockingLeagueHttpClient blockingLeagueHttpClient) {
        LeagueHttpClients.blockingLeagueHttpClient = blockingLeagueHttpClient;
    }
}
