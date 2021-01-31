package com.bonepl.chromaleague.rest;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LeagueHttpClient {
    private static final Logger LOGGER = Logger.getLogger(LeagueHttpClient.class.getName());

    public static final int DEFAULT_TIMEOUT = 150;
    private static CloseableHttpClient singleFetchLeagueHttpClient = createSingleFetchLeagueHttpClient();
    private static CloseableHttpClient retriableLeagueHttpClient = createRetriableHttpClient();


    private LeagueHttpClient() {
    }

    public static Optional<byte[]> getSingleResponse(String url) {
        return getResponse(singleFetchLeagueHttpClient, url);
    }

    public static Optional<byte[]> getRetriableResponse(String url) {
        return getResponse(retriableLeagueHttpClient, url);
    }

    private static Optional<byte[]> getResponse(final CloseableHttpClient httpClient, final String url) {
        final HttpGet request = new HttpGet(url);
        request.addHeader("Content-type", "application/json; charset=UTF-8");
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            byte[] responseBytes = EntityUtils.toByteArray(response.getEntity());
            if (isResponseValid(responseBytes)) {
                return Optional.of(responseBytes);
            }
        } catch (Exception ex) {
            // it is possible to fail on HTTP connection during API shutdown
            LOGGER.log(Level.FINER, ex, () -> "Error while fetching HTTP response");
        }
        return Optional.empty();
    }

    private static boolean isResponseValid(byte... responseBytes) {
        return !new String(responseBytes, StandardCharsets.UTF_8).contains("RESOURCE_NOT_FOUND");
    }

    private static CloseableHttpClient createSingleFetchLeagueHttpClient() {
        return createCustomLeagueHttpClient()
                .disableAutomaticRetries()
                .build();
    }

    private static CloseableHttpClient createRetriableHttpClient() {
        return createCustomLeagueHttpClient()
                .setRetryHandler(new DefaultHttpRequestRetryHandler(10, false))
                .build();
    }

    private static HttpClientBuilder createCustomLeagueHttpClient() {
        try {
            return HttpClients.custom()
                    .setConnectionManager(createUnsecureConnManager())
                    .setDefaultRequestConfig(createRequestConfig());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex, () -> "Error while creating League HTTP Client");
            return null;
        }
    }

    private static PoolingHttpClientConnectionManager createUnsecureConnManager() {
        return new PoolingHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("https", new SSLConnectionSocketFactory(createUnsecuredSSL()))
                        .build());
    }

    private static RequestConfig createRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(DEFAULT_TIMEOUT)
                .setSocketTimeout(DEFAULT_TIMEOUT).build();
    }

    private static SSLContext createUnsecuredSSL() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            }, new SecureRandom());
            return sslContext;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void shutdown() {
        shutdownHttpClient(singleFetchLeagueHttpClient);
        shutdownHttpClient(retriableLeagueHttpClient);
    }

    private static void shutdownHttpClient(CloseableHttpClient httpClient) {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, e, () -> "Couldn't close League HTTP Client, this can lead to memory leak");
            }
        }
    }

    //TEST ONLY
    static void setLeagueHttpClient(CloseableHttpClient leagueHttpClient) {
        singleFetchLeagueHttpClient = leagueHttpClient;
        retriableLeagueHttpClient = leagueHttpClient;
    }
}
