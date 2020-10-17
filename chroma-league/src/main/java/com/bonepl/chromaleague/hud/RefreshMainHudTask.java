package com.bonepl.chromaleague.hud;

import com.bonepl.chromaleague.EventProcessor;
import com.bonepl.chromaleague.GameState;
import com.bonepl.chromaleague.hud.parts.MainHud;
import com.bonepl.razersdk.RazerSDKClient;

public class RefreshMainHudTask implements Runnable {
    private final RazerSDKClient razerSDKClient;

    public RefreshMainHudTask(RazerSDKClient razerSDKClient) {
        this.razerSDKClient = razerSDKClient;
    }

    @Override
    public void run() {
        if (GameState.hasUnprocessedEvents()) {
            EventProcessor.processEvents();
        }
        razerSDKClient.createKeyboardEffect(new MainHud());
    }
}
