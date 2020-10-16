package com.bonepl.chromaleague.league.hud.parts;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.animation.FramePart;
import com.bonepl.chromaleague.razer.effects.animation.StaticFrame;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.Arrays;

public class Background extends StaticFrame {
    public static final Color BACKGROUND_COLOR = new Color(10, 10, 10);

    public Background() {
        super(new FramePart(Arrays.asList(RzKey.values()), BACKGROUND_COLOR));
    }
}
