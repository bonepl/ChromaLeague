package com.bonepl.chromaleague.league.json;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class LeagueHttpClient {
    private final HttpClient httpClient;

    public LeagueHttpClient() {
        this.httpClient = HttpClient.newBuilder()
                .sslContext(createUnsecuredSSL())
                .connectTimeout(Duration.of(100, ChronoUnit.MILLIS)).build();
    }

    public String fetchData(String url) {
        try {
            return httpClient.send(HttpRequest.newBuilder()
                            .uri(URI.create(url)).build(),
                    HttpResponse.BodyHandlers.ofString()).body();
        } catch (HttpConnectTimeoutException ex) {
            //will occur while game has not started yet
            return null;
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static SSLContext createUnsecuredSSL() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{
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
            }, new SecureRandom());
            return sslContext;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }
}
