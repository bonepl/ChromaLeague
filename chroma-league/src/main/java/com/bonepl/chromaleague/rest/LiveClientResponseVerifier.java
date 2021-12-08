package com.bonepl.chromaleague.rest;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

public final class LiveClientResponseVerifier {
    private static final Logger LOGGER = Logger.getLogger(LiveClientResponseVerifier.class.getName());

    private LiveClientResponseVerifier() {
    }

    public static Optional<byte[]> fetchBytesResponse(HttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return Optional.of(EntityUtils.toByteArray(response.getEntity()));
        } else {
            LOGGER.info("Other STATUS: " + response.getStatusLine().getStatusCode());
        }
        return Optional.empty();
    }
}
