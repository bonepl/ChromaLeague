package com.bonepl.chromaleague.league.rest;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Optional;

public class LeagueHttpClient {
    private final static Logger logger = LogManager.getLogger();
    private static CloseableHttpClient leagueHttpClient;

    public static Optional<String> get(String url) {
        try {
            return Optional.of(EntityUtils.toString(
                    getLeagueHttpClient().execute(new HttpGet(url)).getEntity()));
        } catch (Exception e) {
            logger.debug("Couldn't get data from endpoint: " + url + " - is game running?", e);
        }
        return Optional.empty();
    }

    private static CloseableHttpClient getLeagueHttpClient() {
        if (leagueHttpClient == null) {
            leagueHttpClient = createLeagueHttpClient();
        }
        return leagueHttpClient;
    }

    private static CloseableHttpClient createLeagueHttpClient() {
        return HttpClients.custom()
                .setConnectionManager(createUnsecureConnManager())
                .setDefaultRequestConfig(createRequestConfig()).build();
    }

    private static PoolingHttpClientConnectionManager createUnsecureConnManager() {
        return new PoolingHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("https", new SSLConnectionSocketFactory(createUnsecuredSSL()))
                        .build());
    }

    private static RequestConfig createRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(100)
                .setSocketTimeout(100).build();
    }

    private static SSLContext createUnsecuredSSL() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            }, new SecureRandom());
            return sslContext;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    public static void shutdown() {
        if (leagueHttpClient != null) {
            try {
                leagueHttpClient.close();
            } catch (IOException e) {
                logger.warn("Couldn't close League HTTP Client, this can lead to memory leak", e);
            } finally {
                leagueHttpClient = null;
            }
        }
    }
}
