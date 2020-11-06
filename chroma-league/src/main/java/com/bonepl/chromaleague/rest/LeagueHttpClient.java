package com.bonepl.chromaleague.rest;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
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
    private static CloseableHttpClient leagueHttpClient = createLeagueHttpClient();

    private LeagueHttpClient() {
    }

    public static Optional<String> getResponse(String url) {
        try (CloseableHttpResponse response = leagueHttpClient.execute(new HttpGet(url))) {
            final String json = EntityUtils.toString(response.getEntity());
            if (!json.contains("RESOURCE_NOT_FOUND")) {
                return Optional.of(json);
            }
        } catch (Exception ex) {
            // it is possible to fail on HTTP connection during API shutdown
            LOGGER.log(Level.SEVERE, ex, () -> "Error while fetching HTTP response");
        }
        return Optional.empty();
    }

    private static CloseableHttpClient createLeagueHttpClient() {
        try {
            return HttpClients.custom()
                    .setConnectionManager(createUnsecureConnManager())
                    .setDefaultRequestConfig(createRequestConfig())
                    .disableAutomaticRetries()
                    .build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex, () -> "Error while fetching HTTP response");
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
        if (leagueHttpClient != null) {
            try {
                leagueHttpClient.close();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, e, () -> "Couldn't close League HTTP Client, this can lead to memory leak");
            } finally {
                leagueHttpClient = null;
            }
        }
    }

    //TEST ONLY
    static void setLeagueHttpClient(CloseableHttpClient leagueHttpClient) {
        LeagueHttpClient.leagueHttpClient = leagueHttpClient;
    }
}
