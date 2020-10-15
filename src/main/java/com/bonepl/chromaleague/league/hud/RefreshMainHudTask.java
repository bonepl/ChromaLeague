package com.bonepl.chromaleague.league.hud;

import com.bonepl.chromaleague.league.EventProcessor;
import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.hud.parts.*;
import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.keyboard.LayeredCustomEffect;

public class RefreshMainHudTask implements Runnable {
    private final RazerSDKClient razerSDKClient;

    public RefreshMainHudTask(RazerSDKClient razerSDKClient) {
        this.razerSDKClient = razerSDKClient;
    }

    @Override
    public void run() {
        if (GameState.hasUnprocessedEvents()) {
            EventProcessor.processEvents(razerSDKClient);
        }
        razerSDKClient.createKeyboardEffect(new MainHud());
    }
}
