package com.bonepl.chromaleague.razer;

import com.bonepl.chromaleague.razer.sdk.RzDevice;
import com.bonepl.chromaleague.razer.sdk.RzDeviceInfo;
import com.bonepl.chromaleague.razer.sdk.RzDeviceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RazerSDKClientTest {

    @Test
    public void queryBlackwidow() {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            RzDeviceInfo rzDeviceInfo = razerSDKClient.queryDevice(RzDevice.BLACKWIDOW_CHROMA);
            assertEquals(1, rzDeviceInfo.isConnected);
            assertEquals(RzDeviceType.KEYBOARD, RzDeviceType.fromSDKType(rzDeviceInfo.type));
        }
    }

    @Test
    public void queryManowar() {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            RzDeviceInfo rzDeviceInfo = razerSDKClient.queryDevice(RzDevice.MANOWAR_CHROMA);
            assertEquals(0, rzDeviceInfo.isConnected);
            assertEquals(RzDeviceType.HEADSET, RzDeviceType.fromSDKType(rzDeviceInfo.type));
        }
    }
}