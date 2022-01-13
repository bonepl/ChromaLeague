package net.booone.chromaleague.rest.http.client;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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

public abstract class AbstractLeagueHttpClient {
    public static final int DEFAULT_TIMEOUT = 150;
    private final CloseableHttpClient httpClient = createLeagueHttpClient();

    protected abstract CloseableHttpClient createLeagueHttpClient();

    protected <T> T execute(final String url, ResponseHandler<T> responseHandler) throws IOException {
        return getHttpClient().execute(jsonHttpGet(url), responseHandler);
    }

    protected HttpGet jsonHttpGet(final String url) {
        final HttpGet request = new HttpGet(url);
        request.addHeader("Content-type", "application/json; charset=UTF-8");
        return request;
    }

    protected PoolingHttpClientConnectionManager createUnsecureConnManager() {
        return new PoolingHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("https", new SSLConnectionSocketFactory(createUnsecuredSSL()))
                        .build());
    }

    protected RequestConfig createRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(DEFAULT_TIMEOUT)
                .setSocketTimeout(DEFAULT_TIMEOUT).build();
    }

    protected SSLContext createUnsecuredSSL() {
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

    protected HttpClientBuilder commonHttpClientBuilder() {
        return HttpClients.custom()
                .setConnectionManager(createUnsecureConnManager())
                .setDefaultRequestConfig(createRequestConfig());
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }
}
