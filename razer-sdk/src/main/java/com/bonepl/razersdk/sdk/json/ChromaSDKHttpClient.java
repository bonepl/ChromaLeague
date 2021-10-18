package com.bonepl.razersdk.sdk.json;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.logging.Logger;

public final class ChromaSDKHttpClient {
    private static final Logger LOGGER = Logger.getLogger(ChromaSDKHttpClient.class.getName());
    public static final int DEFAULT_TIMEOUT = 5000;

    private ChromaSDKHttpClient() {
    }

    public static CloseableHttpClient create() {
        return HttpClients.createMinimal();
    }
}
