package com.bonepl.razersdk.sdk.json.response;

import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonProperty;

public record SessionInfo(int sessionId, String uri) {

    @JsonCreator
    public SessionInfo(@JsonProperty("sessionid") int sessionId, String uri) {
        this.sessionId = sessionId;
        this.uri = uri;
    }
}
