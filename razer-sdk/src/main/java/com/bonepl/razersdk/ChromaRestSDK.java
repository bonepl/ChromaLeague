package com.bonepl.razersdk;

import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.IFrame;
import com.bonepl.razersdk.sdk.HeartbeatTask;
import com.bonepl.razersdk.sdk.SdkRequestExecutor;
import com.bonepl.razersdk.sdk.json.request.Init;
import com.bonepl.razersdk.sdk.json.request.KeyboardEffect;
import com.bonepl.razersdk.sdk.json.response.Result;
import com.bonepl.razersdk.sdk.json.response.SessionInfo;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Main class for communication with Razer Chroma REST SDK.
 * <br><br>
 * This class implements {@link AutoCloseable} interface, so it is recommended
 * to use it with try-with-resources.
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
public final class ChromaRestSDK extends SdkRequestExecutor {
    private static final Logger LOGGER = Logger.getLogger(ChromaRestSDK.class.getName());
    private static final long INIT_SLEEP_TIME = 2000L;
    private final SessionInfo currentSession;
    private final ScheduledExecutorService heartbeatExecutor;

    static {
        final InputStream inputStream = ChromaRestSDK.class.getClassLoader().getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(inputStream);
        } catch (final IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, e, () -> "Could not load default logging.properties file for ChromaRestSDK");
        }
    }

    /**
     * Create and initialize connection to Chroma-enabled Razer device
     */
    public ChromaRestSDK() {
        super(HttpClients.createMinimal());
        currentSession = init();
        heartbeatExecutor = Executors.newSingleThreadScheduledExecutor();
        heartbeatExecutor.scheduleAtFixedRate(new HeartbeatTask(getHttpClient(), currentSession), 0L, 5L, TimeUnit.SECONDS);
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
        final HttpPut keyboardEffectRequest = new HttpPut(currentSession.uri() + "/keyboard");
        keyboardEffectRequest.setEntity(createKeyboardEffectParameter(frame));
        final String result = executeRequest(keyboardEffectRequest);
        final Result effectResponse = JsonIterator.deserialize(result, Result.class);
        if (effectResponse.result() != 0) {
            LOGGER.severe(() -> "Error from Razer SDK Effect response: " + result);
        }
    }

    private static StringEntity createKeyboardEffectParameter(IFrame frame) {
        final KeyboardEffect keyboardEffect = new KeyboardEffect(frame.getFrame().getKeysToColors());
        final String keyboardEffectJson = JsonStream.serialize(keyboardEffect);
        try {
            return new StringEntity(keyboardEffectJson);
        } catch (UnsupportedEncodingException e) {
            LOGGER.log(Level.SEVERE, e, () -> "Error while creating KeyboardEffect");
            throw new IllegalStateException(e);
        }
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

    private static StringEntity createJsonInitParameter() {
        try {
            final String serialize = JsonStream.serialize(new Init());
            return new StringEntity(serialize);
        } catch (UnsupportedEncodingException e) {
            LOGGER.log(Level.SEVERE, e, () -> "Error while creating Json Init parameter");
            throw new IllegalStateException(e);
        }
    }

    /**
     * Close and disconnect Chroma-enabled Razer device
     */
    @Override
    public void close() {
        heartbeatExecutor.shutdown();
        final HttpDelete unInitRequest = new HttpDelete(currentSession.uri());
        final String unInitResponseJson = executeRequest(unInitRequest);
        final Result result = JsonIterator.deserialize(unInitResponseJson, Result.class);
        LOGGER.info(String.format("Razer Chroma SDK session %d ended with code %d",
                currentSession.sessionId(), result.result()));
        super.close();
    }
}
