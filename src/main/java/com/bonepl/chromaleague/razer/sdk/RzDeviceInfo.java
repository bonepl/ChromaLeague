package com.bonepl.chromaleague.razer.sdk;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class RzDeviceInfo extends Structure {
    private final RzDevice rzDevice;
    public byte type;
    public int isConnected;

    public RzDeviceInfo(RzDevice rzDevice) {
        this.rzDevice = rzDevice;
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("type", "isConnected");
    }

    @Override
    public String toString() {
        return "Razer device " + rzDevice +
                " { type = " + RzDeviceType.fromSDKType(type) +
                ", isConnected = " + (isConnected == 1) + " }";
    }
}
