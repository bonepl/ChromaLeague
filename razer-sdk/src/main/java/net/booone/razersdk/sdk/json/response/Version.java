package net.booone.razersdk.sdk.json.response;

import com.jsoniter.annotation.JsonCreator;

public record Version(String core, String device, String version) {
    @JsonCreator
    public Version {
    }
}
