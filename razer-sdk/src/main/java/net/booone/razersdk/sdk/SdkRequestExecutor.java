package net.booone.razersdk.sdk;

import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SdkRequestExecutor implements Closeable {
    private static final Logger LOGGER = Logger.getLogger(SdkRequestExecutor.class.getName());
    private final CloseableHttpClient httpClient;

    public SdkRequestExecutor(CloseableHttpClient instance) {
        httpClient = instance;
    }

    protected String executeRequest(HttpUriRequest request) {
        try {
            return execute(request);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e, () -> "Error while executing SDK Http request");
            throw new IllegalStateException(e);
        }
    }

    private String execute(HttpUriRequest request) throws IOException {
        return httpClient.execute(request, response -> EntityUtils.toString(response.getEntity()));
    }

    @Override
    public void close() {
        try {
            httpClient.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e, () -> "Couldn't close Chroma SDK Http Client");
        }
    }
}
