package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.Frame;

/**
 * vladimir uses resource type: GNARFURY
 * shyvana uses resource type: DRAGONFURY
 */
public class EnergyBar extends AnimatedFrame {

    @Override
    public Frame getFrame() {
        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(),
                GameStateHelper.getResourcePercentage(), Color.YELLOW));
        return super.getFrame();
    }
}
