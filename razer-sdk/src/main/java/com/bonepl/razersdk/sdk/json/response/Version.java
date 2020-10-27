package com.bonepl.razersdk.sdk.json.response;

import com.jsoniter.annotation.JsonCreator;

public class Version {
    private final String core;
    private final String device;
    private final String version;

    @JsonCreator
    public Version(String core, String device, String version) {
        this.core = core;
        this.device = device;
        this.version = version;
    }

    @Override
    public String toString() {
        return String.format("Razer SDK { version: %s, core: %s, device: %s }",
                version, core, device);
    }
}
