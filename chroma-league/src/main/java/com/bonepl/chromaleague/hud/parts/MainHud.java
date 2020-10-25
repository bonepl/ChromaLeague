package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.chromaleague.hud.animations.*;
import com.bonepl.razersdk.animation.LayeredFrame;

public class MainHud extends LayeredFrame {
    private static final GoldAnimation goldAnimation = new GoldAnimation();
    private static final LevelUpAnimation levelUpAnimation = new LevelUpAnimation();
    private static final EventAnimation eventAnimation = new EventAnimation();
    private static final DragonSoulAnimation dragonSoulAnimation = new DragonSoulAnimation();
    private static final ElderBuffAnimation elderBuffAnimation = new ElderBuffAnimation();
    private static boolean playerDead;

    public MainHud() {
        addFrame(new Background());
        addFrame(new HpBar());
        addFrame(new ResourceBar());
        addFrame(new KilledDragonBar());
        addFrame(new AssistKillingSpreeBar());
        addFrame(dragonSoulAnimation);
        addFrame(goldAnimation);
        addFrame(levelUpAnimation);
        if (GameStateHelper.hasElderBuff()) {
            addFrame(elderBuffAnimation);
        }
        handleRespawnEvent();
        if (eventAnimation.hasFrame()) {
            addFrame(eventAnimation);
        }
        System.out.println();
    }

    private static void handleRespawnEvent() {
        final boolean activePlayerAlive = GameStateHelper.isActivePlayerAlive();
        if (playerDead) {
            if (activePlayerAlive) {
                playerDead = false;
                EventAnimation.addFrames(new RespawnAnimation());
            }
        } else {
            if (!activePlayerAlive) {
                playerDead = true;
            }
        }
    }
}
