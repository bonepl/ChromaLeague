package com.bonepl.chromaleague.tasks;

import com.bonepl.razersdk.SdkConnectivityChecker;

public class SdkConnectivityCheckTask implements Runnable {
    @Override
    public void run() {
        try (final SdkConnectivityChecker sdkConnectivityChecker = new SdkConnectivityChecker()) {
            sdkConnectivityChecker.checkSdkConnectivity();
        }
    }
}
