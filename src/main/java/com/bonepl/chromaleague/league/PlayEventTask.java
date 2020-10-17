package com.bonepl.chromaleague.league;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.animation.IFrame;

public class PlayEventTask implements Runnable {
    private final RazerSDKClient razerSDKClient;
    private final IFrame iFrame;

    public PlayEventTask(RazerSDKClient razerSDKClient, IFrame animation) {
        this.razerSDKClient = razerSDKClient;
        this.iFrame = animation;
    }

    @Override
    public void run() {
        if (iFrame.hasFrame()) {
            razerSDKClient.createKeyboardEffect(iFrame.getFrame());
        }
    }
}
