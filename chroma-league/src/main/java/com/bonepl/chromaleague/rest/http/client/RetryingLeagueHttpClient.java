package com.bonepl.chromaleague.rest.http.client;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;

public final class RetryingLeagueHttpClient extends AbstractLeagueHttpClient {
    public CloseableHttpClient createLeagueHttpClient() {
        return commonHttpClientBuilder()
                .addInterceptorFirst(new RetryOnNon200StatusInterceptor())
                .addInterceptorLast(new RetryOnContentTooSmallInterceptor())
                .setRetryHandler(new StandardHttpRequestRetryHandler(10, true))
                .build();
    }
}
