package com.bonepl.chromaleague.rest.http.client;

import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class IndefiniteRetryHandler extends StandardHttpRequestRetryHandler {
    public IndefiniteRetryHandler() {
        super(3, true);
    }

    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        return super.retryRequest(exception, 1, context);
    }
}
