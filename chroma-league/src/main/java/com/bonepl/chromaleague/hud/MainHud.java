package com.bonepl.chromaleague.hud;

import com.bonepl.chromaleague.hud.animations.RespawnAnimation;
import com.bonepl.chromaleague.hud.parts.*;
import com.bonepl.chromaleague.hud.parts.dragons.DragonBar;
import com.bonepl.chromaleague.hud.parts.health.HpBar;
import com.bonepl.chromaleague.hud.parts.resource.ManaBar;
import com.bonepl.chromaleague.hud.parts.resource.ResourceBars;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.IFrame;
import com.bonepl.razersdk.animation.LayeredFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainHud extends LayeredFrame {
    private static final Logger logger = LogManager.getLogger();
    private static final GoldBar GOLD_BAR = new GoldBar();
    private static final LevelUpBar LEVEL_UP_BAR = new LevelUpBar();
    private static final EventAnimator EVENT_ANIMATOR = new EventAnimator();
    private static final DragonBar DRAGON_BAR = new DragonBar();
    private static final HpBar HP_BAR = new HpBar();
    private static IFrame resourceBar;
    private static boolean playerDead;

    public MainHud() {
        try {
            addFrame(new Background());
            addFrame(HP_BAR);
            addFrame(new ManaBar());
            addFrame(new AssistKillingSpreeBar());
            addFrame(DRAGON_BAR);
            addFrame(GOLD_BAR);
            addFrame(LEVEL_UP_BAR);
            handleRespawnEvent();
            if (EVENT_ANIMATOR.hasFrame()) {
                addFrame(EVENT_ANIMATOR);
            }
        } catch (Exception ex) {
            logger.error("Error while drawing main HUD ", ex);
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

    private static IFrame getResourceBar() {
        if (resourceBar == null) {
            resourceBar = ResourceBars.getResourceBarForActivePlayerChampion();
        }
        return resourceBar;
    }

    public static void clearResourceBar() {
        resourceBar = null;
    }
}
