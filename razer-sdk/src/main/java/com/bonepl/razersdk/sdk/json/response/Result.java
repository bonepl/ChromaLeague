package com.bonepl.razersdk.sdk.json.response;

import com.jsoniter.annotation.JsonCreator;

public class Result {
    private final int result;

    @JsonCreator
    public Result(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }
}
