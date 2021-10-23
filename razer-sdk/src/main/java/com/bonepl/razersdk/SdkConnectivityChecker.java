package com.bonepl.razersdk;

import com.bonepl.razersdk.sdk.SdkRequestExecutor;
import com.bonepl.razersdk.sdk.json.response.Version;
import com.jsoniter.JsonIterator;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.util.logging.Logger;

public class SdkConnectivityChecker extends SdkRequestExecutor {
    private static final Logger LOGGER = Logger.getLogger(SdkConnectivityChecker.class.getName());

    public SdkConnectivityChecker() {
        super(HttpClients.createMinimal());
    }

    public void checkSdkConnectivity() {
        final Version version = versionInfo();
        LOGGER.info(() -> "Detected Razer Chroma REST Api " + version);
    }

    public Version versionInfo() {
        final HttpGet versionInfoRequest = new HttpGet("http://localhost:54235/razer/chromasdk");
        final String versionInfoJson = executeRequest(versionInfoRequest);
        return JsonIterator.deserialize(versionInfoJson, Version.class);
    }
}
