package net.booone.razersdk.sdk;

import com.jsoniter.JsonIterator;
import net.booone.razersdk.ChromaRestSDKSession;
import net.booone.razersdk.sdk.json.response.Heartbeat;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

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
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }
}
