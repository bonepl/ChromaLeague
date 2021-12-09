package com.bonepl.chromaleague.rest.http.handlers;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PlayerNameResponseHandler extends AbstractHttpOkBytesHandler
        implements ResponseHandler<String> {

    @Override
    public String handleResponse(HttpResponse response) throws IOException {
        return fetchBytesResponse(response)
                .map(bytes -> new String(bytes, StandardCharsets.UTF_8))
                .map(str -> str.replaceAll("\"", "").trim())
                .orElseThrow(() -> new IllegalStateException("Couldn't fetch PlayerName"));
    }
}