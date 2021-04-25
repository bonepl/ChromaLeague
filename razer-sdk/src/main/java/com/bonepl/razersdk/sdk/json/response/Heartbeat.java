package com.bonepl.razersdk.sdk.json.response;

import com.jsoniter.annotation.JsonCreator;

public record Heartbeat(int tick) {
    @JsonCreator
    public Heartbeat {
    }
}
