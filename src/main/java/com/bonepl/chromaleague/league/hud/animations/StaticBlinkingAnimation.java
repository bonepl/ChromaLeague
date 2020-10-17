package com.bonepl.chromaleague.league.hud.animations;

import com.bonepl.chromaleague.league.hud.parts.Background;
import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.animation.Animation;
import com.bonepl.chromaleague.razer.effects.animation.Frame;

public class StaticBlinkingAnimation extends Animation {
    private final int times;
    private final Color color;

    public StaticBlinkingAnimation(int times, Color color) {
        this.times = times;
        this.color = color;
    }

    public void runEffect(RazerSDKClient razerSDKClient) {
        for (int i = 0; i < times; i++) {
            try {
                razerSDKClient.createKeyboardEffect(new Frame(color));
                Thread.sleep(50);
                razerSDKClient.createKeyboardEffect(new Background());
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
