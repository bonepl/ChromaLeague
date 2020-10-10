package com.bonepl.chromaleague.razer;

import com.bonepl.chromaleague.razer.effects.keyboard.SDKKeyboardEffect;
import com.bonepl.chromaleague.razer.sdk.RzChromaSDK64;
import com.bonepl.chromaleague.razer.sdk.RzDevice;
import com.bonepl.chromaleague.razer.sdk.RzDeviceInfo;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RazerSDKClient implements AutoCloseable {
    private static final Logger logger = LogManager.getLogger();
    private final RzChromaSDK64 rzChromaSDK64;

    public RazerSDKClient() {
        rzChromaSDK64 = Native.load("RzChromaSDK64", RzChromaSDK64.class);
        init();
    }

    /**
     * Initialization requires about 1s before accepting effect
     * or it drops frames (?)
     */
    private void init() {
        rzChromaSDK64.Init();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public RzDeviceInfo queryDevice(RzDevice rzDevice) {
        RzDeviceInfo rzDeviceInfo = new RzDeviceInfo(rzDevice);
        rzChromaSDK64.QueryDevice(rzDevice.getGuid(), rzDeviceInfo);
        logger.info("Queried device: " + rzDeviceInfo);
        return rzDeviceInfo;
    }

    public void createKeyboardEffect(SDKKeyboardEffect effect) {
        int result = rzChromaSDK64.CreateKeyboardEffect(
                effect.getSDKKeyboardEffectType().getRzSDKKeyboardEffectType(),
                effect.getEffect(), Pointer.NULL);
        if (result != 0) {
            logger.error("Creating keyboard effect returned error " + result);
        }
    }

    @Override
    public void close() {
        rzChromaSDK64.UnInit();
    }
}
