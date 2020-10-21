package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.GameStateHelper;
import com.bonepl.chromaleague.hud.animations.*;
import com.bonepl.razersdk.animation.LayeredFrame;

public class MainHud extends LayeredFrame {
    private static final GoldAnimation goldAnimation = new GoldAnimation();
    private static final LevelUpAnimation levelUpAnimation = new LevelUpAnimation();
    private static final EventAnimation eventAnimation = new EventAnimation();
    private static final DragonSoulAnimation dragonSoulAnimation = new DragonSoulAnimation();
    private static final ElderBuffAnimation elderBuffAnimation = new ElderBuffAnimation();

    public MainHud() {
        addFrame(new Background());
        addFrame(new HpBar());
        addFrame(new ResourceBar());
        addFrame(new KilledDragonBar());
        addFrame(dragonSoulAnimation);
        addFrame(goldAnimation);
        addFrame(levelUpAnimation);
        if (GameStateHelper.hasElderBuff()) {
            addFrame(elderBuffAnimation.getFrame());
        }
        if (eventAnimation.hasFrame()) {
            addFrame(eventAnimation);
        }
    }
}
