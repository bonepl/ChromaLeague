package com.bonepl.razersdk.sdk.json.response;

import com.jsoniter.annotation.JsonCreator;

public class Heartbeat {
    private final int tick;

    @JsonCreator
    public Heartbeat(int tick) {
        this.tick = tick;
    }

    @Override
    public String toString() {
        return "Heartbeat tick " + tick;
    }
}
