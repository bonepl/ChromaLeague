package com.bonepl.chromaleague.hud;

import com.bonepl.chromaleague.hud.animations.RespawnAnimation;
import com.bonepl.chromaleague.hud.parts.*;
import com.bonepl.chromaleague.hud.parts.dragons.DragonBar;
import com.bonepl.chromaleague.hud.parts.health.HealthBar;
import com.bonepl.chromaleague.hud.parts.resource.ResourceBars;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.IFrame;
import com.bonepl.razersdk.animation.LayeredFrame;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainHud extends AnimatedFrame {
    private static final Logger LOGGER = Logger.getLogger(MainHud.class.getName());

    private final GoldBar goldBar = new GoldBar();
    private final LevelUpBar levelUpBar = new LevelUpBar();
    private final EventAnimation eventAnimation = new EventAnimation();
    private final DragonBar dragonBar = new DragonBar();
    private final HealthBar healthBar = new HealthBar();
    private IFrame resourceBar;

    @Override
    public Frame getFrame() {
        final LayeredFrame mainHudFrame = new LayeredFrame();
        try {
            mainHudFrame.addFrame(new Background());
            if (GameStateHelper.isActivePlayerAlive()) {
                mainHudFrame.addFrame(healthBar);
                mainHudFrame.addFrame(getResourceBar());
            }
            mainHudFrame.addFrame(new AssistKillingSpreeBar());
            mainHudFrame.addFrame(dragonBar);
            mainHudFrame.addFrame(goldBar);
            mainHudFrame.addFrame(levelUpBar);
            if(GameStateHelper.shouldPlayRespawnAnimation()){
                eventAnimation.addAnimation(new RespawnAnimation());
            }
            if (eventAnimation.hasFrame()) {
                mainHudFrame.addFrame(eventAnimation);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex, () -> "Error while drawing main HUD ");
        }
        addAnimationFrame(mainHudFrame);
        return super.getFrame();
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
