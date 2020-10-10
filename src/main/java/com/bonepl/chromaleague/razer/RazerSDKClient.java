package com.bonepl.chromaleague.razer;

import com.bonepl.chromaleague.razer.sdk.RzChromaSDK64;
import com.bonepl.chromaleague.razer.sdk.RzDevice;
import com.bonepl.chromaleague.razer.sdk.RzDeviceInfo;
import com.sun.jna.Native;

public class RazerSDKClient implements AutoCloseable {

    private final RzChromaSDK64 rzChromaSDK64;

    public RazerSDKClient() {
        rzChromaSDK64 = Native.load("RzChromaSDK64", RzChromaSDK64.class);
        rzChromaSDK64.Init();
    }

    public RzDeviceInfo queryDevice(RzDevice rzDevice) {
        RzDeviceInfo rzDeviceInfo = new RzDeviceInfo(rzDevice);
        rzChromaSDK64.QueryDevice(rzDevice.getGuid(), rzDeviceInfo);
        System.out.println(rzDeviceInfo);
        return rzDeviceInfo;
    }

    @Override
    public void close() {
        rzChromaSDK64.UnInit();
    }
}
