package com.bonepl.chromaleague.rest;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Optional;

public class LiveClientResponseVerifier {
    public Optional<byte[]> fetchBytesResponse(HttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return Optional.of(EntityUtils.toByteArray(response.getEntity()));
        }
        return Optional.empty();
    }
}
