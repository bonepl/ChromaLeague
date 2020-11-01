package com.bonepl.chromaleague.hud;

import com.bonepl.chromaleague.hud.animations.RespawnAnimation;
import com.bonepl.chromaleague.hud.parts.*;
import com.bonepl.chromaleague.hud.parts.dragons.DragonBar;
import com.bonepl.chromaleague.hud.parts.health.HpBar;
import com.bonepl.chromaleague.hud.parts.resource.ResourceBars;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.IFrame;
import com.bonepl.razersdk.animation.LayeredFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainHud extends AnimatedFrame {
    private final Logger logger = LogManager.getLogger();
    private final GoldBar goldBar = new GoldBar();
    private final LevelUpBar levelUpBar = new LevelUpBar();
    private final EventAnimation eventAnimation = new EventAnimation();
    private final DragonBar dragonBar = new DragonBar();
    private final HpBar hpBar = new HpBar();
    private IFrame resourceBar;
    private boolean playerDead;

    @Override
    public Frame getFrame() {
        final LayeredFrame mainHudFrame = new LayeredFrame();
        try {
            mainHudFrame.addFrame(new Background());
            if (GameStateHelper.isActivePlayerAlive()) {
                mainHudFrame.addFrame(hpBar);
                mainHudFrame.addFrame(getResourceBar());
            }
            mainHudFrame.addFrame(new AssistKillingSpreeBar());
            mainHudFrame.addFrame(dragonBar);
            mainHudFrame.addFrame(goldBar);
            mainHudFrame.addFrame(levelUpBar);
            handleRespawnEvent();
            if (eventAnimation.hasFrame()) {
                mainHudFrame.addFrame(eventAnimation);
            }
        } catch (Exception ex) {
            logger.error("Error while drawing main HUD ", ex);
        }
        addAnimationFrame(mainHudFrame);
        return super.getFrame();
    }

    private void handleRespawnEvent() {
        final boolean activePlayerAlive = GameStateHelper.isActivePlayerAlive();
        if (playerDead) {
            if (activePlayerAlive) {
                playerDead = false;
                eventAnimation.addAnimation(new RespawnAnimation());
            }
        } else {
            if (!activePlayerAlive) {
                playerDead = true;
            }
        }
    }
    private IFrame getResourceBar() {
        if (resourceBar == null) {
            resourceBar = ResourceBars.getResourceBarForActivePlayerChampion();
        }
        return resourceBar;
    }

    public EventAnimation getEventAnimation() {
        return eventAnimation;
    }
}
