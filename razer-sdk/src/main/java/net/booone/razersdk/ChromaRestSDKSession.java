package net.booone.razersdk;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import net.booone.razersdk.sdk.SdkRequestExecutor;
import net.booone.razersdk.sdk.json.request.Init;
import net.booone.razersdk.sdk.json.response.Result;
import net.booone.razersdk.sdk.json.response.SessionInfo;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ChromaRestSDKSession extends SdkRequestExecutor {
    private static final long INIT_SLEEP_TIME = 2000L;
    private static final Logger LOGGER = Logger.getLogger(ChromaRestSDK.class.getName());

    private SessionInfo currentSession;

    public ChromaRestSDKSession(CloseableHttpClient instance) {
        super(instance);
        currentSession = init();
    }

    public SessionInfo getCurrentSession() {
        return currentSession;
    }

    public synchronized void refreshSession() {
        LOGGER.info(() -> "Refreshing ChromaRestSDK session");
        currentSession = init();
    }

    private SessionInfo init() {
        final HttpPost initRequest = new HttpPost("http://localhost:54235/razer/chromasdk/");
        initRequest.setEntity(createJsonInitParameter());
        initRequest.setHeader("Content-type", "application/json");
        final String sessionInfoJson = executeRequest(initRequest);
        final SessionInfo sessionInfo = JsonIterator.deserialize(sessionInfoJson, SessionInfo.class);
        LOGGER.info(() -> "Initialized ChromaRestSDK connection { sessionId: " + sessionInfo.sessionId() + " }");
        try {
            Thread.sleep(INIT_SLEEP_TIME);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, e, () -> "ChromaRestSDK initialization interrupted");
            Thread.currentThread().interrupt();
        }
        return sessionInfo;
    }

    public void unInit() {
        final HttpDelete unInitRequest = new HttpDelete(currentSession.uri());
        final String unInitResponseJson = executeRequest(unInitRequest);
        final Result result = JsonIterator.deserialize(unInitResponseJson, Result.class);
        LOGGER.info(String.format("Razer Chroma SDK session %d ended with code %d",
                currentSession.sessionId(), result.result()));
    }

    private static StringEntity createJsonInitParameter() {
        final String serialize = JsonStream.serialize(new Init());
        return new StringEntity(serialize);
    }
}
