package net.booone.chromaleague.rest.http.handlers;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PlayerNameResponseHandler extends HttpOkBytesHandler
        implements HttpClientResponseHandler<String> {

    @Override
    public String handleResponse(ClassicHttpResponse response) throws IOException {
        return fetchBytesResponse(response)
                .map(bytes -> new String(bytes, StandardCharsets.UTF_8))
                .map(str -> str.replaceAll("^\"(.+)\"$", "$1"))
                .orElseThrow(() -> new IllegalStateException("Couldn't fetch PlayerName"));
    }
}