package com.bonepl.razersdk;

import com.bonepl.razersdk.animation.IFrame;
import com.bonepl.razersdk.sdk.RzChromaSDK64;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public void createKeyboardEffect(IFrame frame) {
        int result = rzChromaSDK64.CreateKeyboardEffect(2, frame.getFrame().toCustomEffect().getEffect(), Pointer.NULL);
        if (result != 0) {
            LOGGER.error("Creating keyboard effect returned error {}", result);
        }
    }

    @Override
    public void close() {
        final int unInitReturnCode = rzChromaSDK64.UnInit();
        if (unInitReturnCode != 0) {
            LOGGER.error("Error during un-initialization of RazerSDKClient, returned code {}", unInitReturnCode);
        }
    }
}
