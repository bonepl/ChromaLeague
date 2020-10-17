package com.bonepl.chromaleague.league.hud.parts;

import com.bonepl.chromaleague.league.hud.animations.EventAnimation;
import com.bonepl.chromaleague.league.hud.animations.GoldAnimation;
import com.bonepl.chromaleague.league.hud.animations.LevelUpAnimation;
import com.bonepl.chromaleague.razer.effects.animation.LayeredFrame;

public class MainHud extends LayeredFrame {
    private static final GoldAnimation goldAnimation = new GoldAnimation();
    private static final LevelUpAnimation levelUpAnimation = new LevelUpAnimation();
    private static final EventAnimation eventAnimation = new EventAnimation();

    public MainHud() {
        withFrame(new Background());
        withFrame(new HpBar());
        withFrame(new ResourceBar());
        withFrame(goldAnimation);
        withFrame(levelUpAnimation);
        if (eventAnimation.hasFrame()) {
            withFrame(eventAnimation);
        }
    }
}
