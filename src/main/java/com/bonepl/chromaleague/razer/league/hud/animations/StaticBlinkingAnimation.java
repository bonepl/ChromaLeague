package com.bonepl.chromaleague.razer.league.hud.animations;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.keyboard.StaticEffect;
import com.bonepl.chromaleague.razer.league.hud.Background;

public class StaticBlinkingAnimation {
    private int times;
    private Color color;

    public StaticBlinkingAnimation(int times, Color color) {
        this.times = times;
        this.color = color;
    }

    public void runEffect(RazerSDKClient razerSDKClient) {
        for (int i = 0; i < times; i++) {
            try {
                razerSDKClient.createKeyboardEffect(new StaticEffect(color));
                Thread.sleep(50);
                razerSDKClient.createKeyboardEffect(new Background());
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
