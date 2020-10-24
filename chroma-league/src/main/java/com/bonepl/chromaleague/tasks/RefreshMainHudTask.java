package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.hud.parts.MainHud;
import com.bonepl.razersdk.RazerSDKClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RefreshMainHudTask implements Runnable {
    private static final Logger logger = LogManager.getLogger();
    private RazerSDKClient razerSDKClient;

    public RefreshMainHudTask(RazerSDKClient razerSDKClient) {
        this.razerSDKClient = razerSDKClient;
    }

    @Override
    public void run() {
        razerSDKClient.createKeyboardEffect(new MainHud());
    }
}
