package com.bonepl.chromaleague.razer;

import com.bonepl.chromaleague.league.rest.activeplayer.model.ActivePlayer;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Disabled
public class JsonGet {
    private static final Logger logger = LogManager.getLogger();

    @Test
    void testFetch() throws IOException, InterruptedException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new SecureRandom());

        HttpClient httpClient = HttpClient.newBuilder()
                .sslContext(sslContext)
                .connectTimeout(Duration.of(100, ChronoUnit.MILLIS)).build();
        Path path = Paths.get("E:/tmplolplayer.log");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            while (true) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://127.0.0.1:2999/liveclientdata/activeplayer"))
                        .build();
                try {
                    HttpResponse<String> response =
                            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    bufferedWriter.append(response.body());
                    bufferedWriter.flush();
                    logger.info("response flushed to log");
                } catch (HttpConnectTimeoutException ex) {

                }
                Thread.sleep(5000);
            }
        }
    }

    @Test
    void parseActivePlayer() throws URISyntaxException, IOException {
        final URL resource = this.getClass().getClassLoader().getResource("json/activeplayer.json");
        final String s = Files.readString(new File(resource.toURI()).toPath());
        final ActivePlayer deserialize = JsonIterator.deserialize(s, ActivePlayer.class);
        System.out.println();
    }

    private static TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
    };
}
