package com.bonepl.razersdk;

import com.bonepl.razersdk.sdk.SdkRequestExecutor;
import com.bonepl.razersdk.sdk.json.request.Init;
import com.bonepl.razersdk.sdk.json.response.Result;
import com.bonepl.razersdk.sdk.json.response.SessionInfo;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.UnsupportedEncodingException;
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
        try {
            final String serialize = JsonStream.serialize(new Init());
            return new StringEntity(serialize);
        } catch (UnsupportedEncodingException e) {
            LOGGER.log(Level.SEVERE, e, () -> "Error while creating Json Init parameter");
            throw new IllegalStateException(e);
        }
    }
}
