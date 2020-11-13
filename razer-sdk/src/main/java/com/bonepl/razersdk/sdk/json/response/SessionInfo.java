package com.bonepl.razersdk.sdk.json.response;

import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonProperty;

public class SessionInfo {

    private final int sessionId;
    private final String uri;

    @JsonCreator
    public SessionInfo(@JsonProperty("sessionid") int sessionId, String uri) {
        this.sessionId = sessionId;
        this.uri = uri;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getUri() {
        return uri;
    }
}
