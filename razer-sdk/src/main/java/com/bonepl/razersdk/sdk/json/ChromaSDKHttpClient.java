package com.bonepl.razersdk.sdk.json;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ChromaSDKHttpClient {
    private static final Logger LOGGER = Logger.getLogger(ChromaSDKHttpClient.class.getName());
    public static final int DEFAULT_TIMEOUT = 5000;

    private ChromaSDKHttpClient() {
    }

    public static CloseableHttpClient create() {
        return HttpClients.custom()
                .setConnectionManager(createSelfTrustedConnectionManager())
                .setDefaultRequestConfig(createRequestConfig())
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .disableAutomaticRetries()
                .build();
    }

    private static PoolingHttpClientConnectionManager createSelfTrustedConnectionManager() {
        return new PoolingHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("https", new SSLConnectionSocketFactory(createTrustSelfSignedSSLContext()))
                        .build());
    }

    private static RequestConfig createRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(DEFAULT_TIMEOUT)
                .setSocketTimeout(DEFAULT_TIMEOUT).build();
    }

    private static SSLContext createTrustSelfSignedSSLContext() {
        try {
            return new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) {
                    return true;
                }
            }).build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            LOGGER.log(Level.SEVERE, e, () -> "Error while creating SSL context");
            throw new IllegalStateException(e);
        }
    }
}
