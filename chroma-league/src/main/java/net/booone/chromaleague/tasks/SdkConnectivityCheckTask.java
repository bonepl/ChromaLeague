package net.booone.chromaleague.tasks;

import net.booone.razersdk.SdkConnectivityChecker;

public class SdkConnectivityCheckTask implements Runnable {
    @Override
    public void run() {
        try (final SdkConnectivityChecker sdkConnectivityChecker = new SdkConnectivityChecker()) {
            sdkConnectivityChecker.checkSdkConnectivity();
        }
    }
}
