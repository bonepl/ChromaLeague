package com.bonepl.chromaleague.league.hud.parts;

import com.bonepl.chromaleague.league.hud.animations.GoldAnimation;
import com.bonepl.chromaleague.razer.effects.keyboard.LayeredCustomEffect;

public class MainHud extends LayeredCustomEffect {
    private static final GoldAnimation goldAnimation = new GoldAnimation();

    public MainHud() {
        addCustomKeyboardEffect(new Background());
        addCustomKeyboardEffect(new HpBar());
        addCustomKeyboardEffect(new ResourceBar());
        addCustomKeyboardEffect(goldAnimation);
    }
}
