package net.booone.chromaleague.hud;

import net.booone.chromaleague.hud.animations.LevelUpAnimation;
import net.booone.chromaleague.hud.animations.RespawnAnimation;
import net.booone.chromaleague.hud.animations.RiftAnimation;
import net.booone.chromaleague.hud.parts.AssistKillingSpreeBar;
import net.booone.chromaleague.hud.parts.Background;
import net.booone.chromaleague.hud.parts.EventAnimation;
import net.booone.chromaleague.hud.parts.GoldBar;
import net.booone.chromaleague.hud.parts.dragons.DragonBar;
import net.booone.chromaleague.hud.parts.health.HealthBar;
import net.booone.chromaleague.hud.parts.resource.ResourceBars;
import net.booone.chromaleague.state.GameStateHelper;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.animation.IFrame;
import net.booone.razersdk.animation.LayeredFrame;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainHud extends AnimatedFrame {
    private static final Logger LOGGER = Logger.getLogger(MainHud.class.getName());

    private final GoldBar goldBar = new GoldBar();
    private final LevelUpAnimation levelUpAnimation = new LevelUpAnimation();
    private final EventAnimation eventAnimation = new EventAnimation();
    private final DragonBar dragonBar = new DragonBar();
    private final HealthBar healthBar = new HealthBar();
    private IFrame resourceBar;

    @Override
    public Frame getFrame() {
        final LayeredFrame mainHudFrame = new LayeredFrame();
        try {
            mainHudFrame.addFrame(new Background());
            if (GameStateHelper.shouldPlayRiftAnimation()) {
                eventAnimation.addAnimation(new RiftAnimation());
            }
            if (GameStateHelper.isActivePlayerAlive()) {
                mainHudFrame.addFrame(healthBar);
                mainHudFrame.addFrame(getResourceBar());
            }
            mainHudFrame.addFrame(new AssistKillingSpreeBar());
            mainHudFrame.addFrame(dragonBar);
            mainHudFrame.addFrame(goldBar);
            mainHudFrame.addFrame(levelUpAnimation);
            if (GameStateHelper.shouldPlayRespawnAnimation()) {
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
