package com.bonepl.chromaleague.league.hud.parts;

import com.bonepl.chromaleague.league.hud.animations.GoldAnimation;
import com.bonepl.chromaleague.razer.effects.animation.LayeredFrame;

public class MainHud extends LayeredFrame {
    private static final GoldAnimation goldAnimation = new GoldAnimation();

    public MainHud() {
        withFrame(new Background());
        withFrame(new HpBar());
        withFrame(new ResourceBar());
        withFrame(goldAnimation);
    }
}
