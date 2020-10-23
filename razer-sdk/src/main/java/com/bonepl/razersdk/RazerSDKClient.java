package com.bonepl.razersdk;

import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.IFrame;
import com.bonepl.razersdk.sdk.RzChromaSDK64;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ScheduledExecutorService;

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
 * try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
 *     # animation start
 *     razerSDKClient.createKeyboardEffect(new SimpleFrame(Color.RED));
 *     razerSDKClient.createKeyboardEffect(new SimpleFrame(Color.BLUE));
 *     razerSDKClient.createKeyboardEffect(new SimpleFrame(Color.GREEN));
 *     # animation end
 * }
 * # automatic disconnection
 * </pre>
 */
public class RazerSDKClient implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final RzChromaSDK64 rzChromaSDK64;

    public RazerSDKClient() {
        rzChromaSDK64 = Native.load("RzChromaSDK64", RzChromaSDK64.class);
        init();
    }

    /**
     * Initialization requires about 1s before accepting effect
     * or it drops first frames of animation
     */
    private void init() {
        final int initReturnCode = rzChromaSDK64.Init();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOGGER.error("Error during initialization of RazerSDKClient, returned code {}",
                    initReturnCode, e);
        }
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
     * @param frame {@link IFrame} with available {@link Frame}
     */
    public void createKeyboardEffect(IFrame frame) {
        int result = rzChromaSDK64.CreateKeyboardEffect(2, frame.getFrame().toCustomEffect().getEffect(), Pointer.NULL);
        if (result != 0) {
            LOGGER.error("Creating keyboard effect returned error {}", result);
        }
    }

    /**
     * Close and disconnect Razer device
     */
    @Override
    public void close() {
        final int unInitReturnCode = rzChromaSDK64.UnInit();
        if (unInitReturnCode != 0) {
            LOGGER.error("Error during un-initialization of RazerSDKClient, returned code {}", unInitReturnCode);
        }
    }
}
