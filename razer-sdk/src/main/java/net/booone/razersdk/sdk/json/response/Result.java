package net.booone.razersdk.sdk.json.response;

import com.jsoniter.annotation.JsonCreator;

public record Result(int result) {
    @JsonCreator
    public Result {
    }
}
