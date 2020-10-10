package com.bonepl.chromaleague.razer.sdk;

import java.util.Arrays;

public enum RzDeviceType {
    KEYBOARD(1),
    MOUSE(2),
    HEADSET(3),
    MOUSEPAD(4),
    KEYPAD(5),
    SYSTEM(6),
    SPEAKERS(7),
    INVALID(0);
    private final int type;

    RzDeviceType(int type) {
        this.type = type;
    }

    public static RzDeviceType fromSDKType(byte type) {
        return Arrays.stream(values()).filter(v -> v.type == type).findAny().orElse(INVALID);
    }
}
