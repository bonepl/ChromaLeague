package com.bonepl.chromaleague.rest.http.client;

import org.apache.http.impl.client.CloseableHttpClient;

public final class NonBlockingLeagueHttpClient extends AbstractLeagueHttpClient {
    public CloseableHttpClient createLeagueHttpClient() {
        return commonHttpClientBuilder()
                .disableAutomaticRetries()
                .build();
    }
}
