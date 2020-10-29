package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.SimpleFrame;

public class NoResourceBar extends AnimatedFrame {

    @Override
    public Frame getFrame() {
        addAnimationFrame(new SimpleFrame());
        return super.getFrame();
    }
}
