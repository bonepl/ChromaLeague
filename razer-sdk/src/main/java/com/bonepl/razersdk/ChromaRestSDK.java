package com.bonepl.razersdk;

import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.IFrame;
import com.bonepl.razersdk.sdk.HeartbeatTask;
import com.bonepl.razersdk.sdk.json.ChromaSDKHttpClient;
import com.bonepl.razersdk.sdk.json.request.Init;
import com.bonepl.razersdk.sdk.json.request.KeyboardEffect;
import com.bonepl.razersdk.sdk.json.response.Result;
import com.bonepl.razersdk.sdk.json.response.SessionInfo;
import com.bonepl.razersdk.sdk.json.response.Version;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Main class for communication with Razer Chroma SDK C++ libraries.
 * Note that it is heavy to initialize this class, so try to initialize it once
 * and close only when you want to disconnect from your Razer device.
 * <br><br>
 * This class implements {@link AutoCloseable} interface so it is recommended
 * to use with try-with-resources.
 * <br><br>
 * Recommended usage:
 * <pre>
 * try (ChromaRestSDK chromaRestSDK = new ChromaRestSDK()) {
 *     # animation start
 *     chromaRestSDK.createKeyboardEffect(new SimpleFrame(Color.RED));
 *     chromaRestSDK.createKeyboardEffect(new SimpleFrame(Color.BLUE));
 *     chromaRestSDK.createKeyboardEffect(new SimpleFrame(Color.GREEN));
 *     # animation end
 * }
 * # automatic disconnection
 * </pre>
 */
public class ChromaRestSDK implements AutoCloseable {
    private final static Logger LOGGER = LogManager.getLogger();
    private final CloseableHttpClient httpClient;
    private final SessionInfo sessionInfo;
    private final ScheduledExecutorService heartbeatExecutor;

    /**
     * Create and initialize connection to Chroma-enabled Razer device
     */
    public ChromaRestSDK() {
        this.httpClient = ChromaSDKHttpClient.getInstance();
        printVersionInfo();
        sessionInfo = init();
        heartbeatExecutor = Executors.newSingleThreadScheduledExecutor();
        heartbeatExecutor.scheduleAtFixedRate(new HeartbeatTask(httpClient, sessionInfo), 0, 5, TimeUnit.SECONDS);
    }

    /**
     * Create effect from {@link Frame} obtained
     * from {@link IFrame} object.
     * <br><br>
     * Note that animations passed to this method will be applied
     * immediately to your Razer device and will last until next
     * method invocation or device disconnection.
     * <br><br>
     * It is recommended to use scheduler like {@link ScheduledExecutorService}
     * to invoke this method in fixed rates.
     *
     * @param frame {@link IFrame} with available {@link Frame}
     */
    public void createKeyboardEffect(IFrame frame) {
        final HttpPut keyboardEffectRequest = new HttpPut(sessionInfo.getUri() + "/keyboard");
        keyboardEffectRequest.setEntity(createKeyboardEffectParameter(frame));
        final String result = executeRequest(keyboardEffectRequest);
        final Result effectResponse = JsonIterator.deserialize(result, Result.class);
        if (effectResponse.getResult() != 0) {
            LOGGER.error("Error: " + result);
        }
    }

    private StringEntity createKeyboardEffectParameter(IFrame frame) {
        final KeyboardEffect keyboardEffect = new KeyboardEffect(frame.getFrame().getKeysToColors());
        final String keyboardEffectJson = JsonStream.serialize(keyboardEffect);
        try {
            return new StringEntity(keyboardEffectJson);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e);
            throw new IllegalStateException(e);
        }
    }

    private SessionInfo init() {
        final HttpPost initRequest = new HttpPost("https://chromasdk.io:54236/razer/chromasdk/");
        initRequest.setEntity(createJsonInitParameter());
        initRequest.setHeader("Content-type", "application/json");
        final String sessionInfoJson = executeRequest(initRequest);
        final SessionInfo sessionInfo = JsonIterator.deserialize(sessionInfoJson, SessionInfo.class);
        LOGGER.info("Initialized Razer Chroma SDK connection " + sessionInfo);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
        return sessionInfo;
    }

    private StringEntity createJsonInitParameter() {
        try {
            final String serialize = JsonStream.serialize(new Init());
            return new StringEntity(serialize);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e);
            throw new IllegalStateException(e);
        }
    }

    private void printVersionInfo() {
        final HttpGet versionInfoRequest = new HttpGet("https://chromasdk.io:54236/razer/chromasdk");
        final String versionInfoJson = executeRequest(versionInfoRequest);
        final Version version = JsonIterator.deserialize(versionInfoJson, Version.class);
        LOGGER.info("Detected Razer Chroma REST Api " + version);
    }

    /**
     * Close and disconnect Chroma-enabled Razer device
     */
    @Override
    public void close() {
        heartbeatExecutor.shutdown();
        final HttpDelete unInitRequest = new HttpDelete(sessionInfo.getUri());
        final String unInitResponseJson = executeRequest(unInitRequest);
        final Result result = JsonIterator.deserialize(unInitResponseJson, Result.class);
        LOGGER.info(String.format("Razer Chroma SDK session %d ended with code %d",
                sessionInfo.getSessionId(), result.getResult()));
        try {
            this.httpClient.close();
        } catch (IOException e) {
            LOGGER.error("Exception while closing ChromaSDKHttpClient", e);
            throw new IllegalStateException(e);
        }
    }

    private String executeRequest(HttpUriRequest request) {
        try {
            try (final CloseableHttpResponse execute = httpClient.execute(request)) {
                return EntityUtils.toString(execute.getEntity());
            }
        } catch (IOException e) {
            LOGGER.error("Error while executing SDK Http request", e);
            throw new IllegalStateException(e);
        }
    }
}
