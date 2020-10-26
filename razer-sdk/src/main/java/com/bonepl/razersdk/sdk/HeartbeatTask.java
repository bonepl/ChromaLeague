package com.bonepl.razersdk.sdk;

import com.bonepl.razersdk.sdk.json.response.Heartbeat;
import com.bonepl.razersdk.sdk.json.response.SessionInfo;
import com.jsoniter.JsonIterator;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class HeartbeatTask implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final CloseableHttpClient httpClient;
    private final SessionInfo sessionInfo;

    public HeartbeatTask(CloseableHttpClient httpClient, SessionInfo sessionInfo) {
        this.httpClient = httpClient;
        this.sessionInfo = sessionInfo;
    }

    @Override
    public void run() {
        try {
            final HttpPut heartbeatRequest = new HttpPut(sessionInfo.getUri() + "/heartbeat");
            executeHttpRequest(heartbeatRequest);
        } catch (IOException e) {
            LOGGER.error("Error while executing heartbeat", e);
            throw new IllegalStateException(e);
        }
    }

    private void executeHttpRequest(HttpPut heartbeatRequest) throws IOException {
        try (final CloseableHttpResponse execute = httpClient.execute(heartbeatRequest)) {
            final String heartbeatJson = EntityUtils.toString(execute.getEntity());
            final Heartbeat heartbeat = JsonIterator.deserialize(heartbeatJson, Heartbeat.class);
            LOGGER.debug(heartbeat);
        }
    }
}
