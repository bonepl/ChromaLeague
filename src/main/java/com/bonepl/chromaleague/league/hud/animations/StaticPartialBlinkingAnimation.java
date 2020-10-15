package com.bonepl.chromaleague.league.hud.animations;

import com.bonepl.chromaleague.league.hud.parts.Background;
import com.bonepl.chromaleague.league.hud.parts.MainHud;
import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.keyboard.StaticPartialEffect;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.List;

public class StaticPartialBlinkingAnimation {
    private final int times;
    private final Color color;
    private final List<RzKey> rzKeys;

    public StaticPartialBlinkingAnimation(List<RzKey> rzKeys, int times, Color color) {
        this.rzKeys = rzKeys;
        this.times = times;
        this.color = color;
    }

    public void runEffect(RazerSDKClient razerSDKClient) {
        for (int i = 0; i < times; i++) {
            try {
                razerSDKClient.createKeyboardEffect(new MainHud()
                        .withCustomKeyboardEffect(new StaticPartialEffect(rzKeys, color)));
                Thread.sleep(50);
                razerSDKClient.createKeyboardEffect(new MainHud()
                        .withCustomKeyboardEffect(new StaticPartialEffect(rzKeys, Background.BACKGROUND_COLOR)));
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
