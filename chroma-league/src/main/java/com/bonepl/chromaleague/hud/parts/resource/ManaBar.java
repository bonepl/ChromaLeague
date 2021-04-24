package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.colors.CLColor;
import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Frame;

/**
 * vladimir uses resource type: GNARFURY
 * shyvana uses resource type: DRAGONFURY
 */
public class ManaBar extends AnimatedFrame {
    @Override
    public Frame getFrame() {
        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(),
                GameStateHelper.getResourcePercentage(), CLColor.MANA));
        return super.getFrame();
    }
}
