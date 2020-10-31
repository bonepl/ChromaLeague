package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;

public class MordekaiserShieldBar extends AnimatedFrame {
    @Override
    public Frame getFrame() {
        addAnimationFrame(new ProgressBar(ResourceBars.getResourceBarKeys(), GameStateHelper.getResourcePercentage(), Color.WHITE));
        return super.getFrame();
    }
}
