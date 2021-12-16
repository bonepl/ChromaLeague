package com.bonepl.razersdk.sdk;

import com.bonepl.razersdk.ChromaRestSDKSession;
import com.bonepl.razersdk.sdk.json.response.Heartbeat;
import com.jsoniter.JsonIterator;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public record HeartbeatTask(CloseableHttpClient httpClient,
                            ChromaRestSDKSession chromaRestSDKSession) implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(HeartbeatTask.class.getName());

    @Override
    public void run() {
        try {
            final HttpPut heartbeatRequest = new HttpPut(chromaRestSDKSession.getCurrentSession().uri() + "/heartbeat");
            executeHttpRequest(heartbeatRequest);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e, () -> "Error while executing heartbeat");
            chromaRestSDKSession.refreshSession();
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
