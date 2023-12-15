package net.booone.chromaleague.rest.http.handlers;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.util.Optional;

public class HttpOkBytesHandler {
    public Optional<byte[]> fetchBytesResponse(ClassicHttpResponse response) throws IOException {
        if (response.getCode() == HttpStatus.SC_OK) {
            return Optional.of(EntityUtils.toByteArray(response.getEntity()));
        }
        return Optional.empty();
    }
}
