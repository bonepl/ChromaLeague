package com.bonepl.chromaleague.rest;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

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

public final class NewLeagueHttpClient {
    private static final Logger LOGGER = Logger.getLogger(NewLeagueHttpClient.class.getName());

    public static final int DEFAULT_TIMEOUT = 150;
    private static CloseableHttpClient httpClient = createChromaLeagueHttpClient();

    private NewLeagueHttpClient() {
    }

    public static <T> Optional<T> getResponse(final String url, ResponseHandler<Optional<T>> responseHandler) {
        final HttpGet request = new HttpGet(url);
        request.addHeader("Content-type", "application/json; charset=UTF-8");
        try {
            return httpClient.execute(request, responseHandler);
        } catch (IOException e) {
            //IOException will be thrown when endpoints are down and threads are not shutdown yet
            LOGGER.log(Level.FINER, e, () -> "Error while fetching HTTP response - probably endpoint is down");
        }
        return Optional.empty();
    }

    private static CloseableHttpClient createChromaLeagueHttpClient() {
        return HttpClients.custom()
                .disableAutomaticRetries()
                .setConnectionManager(createUnsecureConnManager())
                .setDefaultRequestConfig(createRequestConfig())
                .build();
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
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }
                    }
            }, new SecureRandom());
            return sslContext;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void shutdown() {
        shutdownHttpClient(httpClient);
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
        httpClient = leagueHttpClient;
    }
}
