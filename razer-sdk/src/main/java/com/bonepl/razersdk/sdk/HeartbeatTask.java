package com.bonepl.razersdk.sdk;

import com.bonepl.razersdk.sdk.json.response.Heartbeat;
import com.bonepl.razersdk.sdk.json.response.SessionInfo;
import com.jsoniter.JsonIterator;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HeartbeatTask implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(HeartbeatTask.class.getName());

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
            LOGGER.log(Level.SEVERE, e, () -> "Error while executing heartbeat");
            throw new IllegalStateException(e);
        }
    }

    private void executeHttpRequest(HttpPut heartbeatRequest) throws IOException {
        try (final CloseableHttpResponse execute = httpClient.execute(heartbeatRequest)) {
            final String heartbeatJson = EntityUtils.toString(execute.getEntity());
            final Heartbeat heartbeat = JsonIterator.deserialize(heartbeatJson, Heartbeat.class);
            LOGGER.finer(heartbeat::toString);
        }
    }
}
