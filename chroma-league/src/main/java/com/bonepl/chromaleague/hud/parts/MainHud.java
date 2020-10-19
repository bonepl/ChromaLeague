package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.hud.animations.EventAnimation;
import com.bonepl.chromaleague.hud.animations.GoldAnimation;
import com.bonepl.chromaleague.hud.animations.LevelUpAnimation;
import com.bonepl.razersdk.animation.LayeredFrame;

public class MainHud extends LayeredFrame {
    private static final GoldAnimation goldAnimation = new GoldAnimation();
    private static final LevelUpAnimation levelUpAnimation = new LevelUpAnimation();
    private static final EventAnimation eventAnimation = new EventAnimation();

    public MainHud() {
        addFrame(new Background());
        addFrame(new HpBar());
        addFrame(new ResourceBar());
        addFrame(goldAnimation);
        addFrame(levelUpAnimation);
        if (eventAnimation.hasFrame()) {
            addFrame(eventAnimation);
        }
    }
}
