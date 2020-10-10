package com.bonepl.chromaleague.razersdk;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class RzDeviceInfo extends Structure {
    public byte type;
    public int isConnected;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("type", "isConnected");
    }
}
