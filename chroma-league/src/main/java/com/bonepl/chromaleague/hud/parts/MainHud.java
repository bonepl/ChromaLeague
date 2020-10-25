package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.hud.animations.ElderBuffAnimation;
import com.bonepl.chromaleague.hud.animations.RespawnAnimation;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.LayeredFrame;

public class MainHud extends LayeredFrame {
    private static final GoldBar GOLD_BAR = new GoldBar();
    private static final LevelUpBar LEVEL_UP_BAR = new LevelUpBar();
    private static final EventAnimator EVENT_ANIMATOR = new EventAnimator();
    private static final DragonSoulBar DRAGON_SOUL_BAR = new DragonSoulBar();
    private static final ElderBuffAnimation elderBuffAnimation = new ElderBuffAnimation();
    private static boolean playerDead;

    public MainHud() {
        addFrame(new Background());
        addFrame(new HpBar());
        addFrame(new ResourceBar());
        addFrame(new KilledDragonBar());
        addFrame(new AssistKillingSpreeBar());
        addFrame(DRAGON_SOUL_BAR);
        addFrame(GOLD_BAR);
        addFrame(LEVEL_UP_BAR);
        if (GameStateHelper.hasElderBuff()) {
            addFrame(elderBuffAnimation);
        }
        handleRespawnEvent();
        if (EVENT_ANIMATOR.hasFrame()) {
            addFrame(EVENT_ANIMATOR);
        }
    }

    private static void handleRespawnEvent() {
        final boolean activePlayerAlive = GameStateHelper.isActivePlayerAlive();
        if (playerDead) {
            if (activePlayerAlive) {
                playerDead = false;
                EventAnimator.addAnimation(new RespawnAnimation());
            }
        } else {
            if (!activePlayerAlive) {
                playerDead = true;
            }
        }
    }
}
