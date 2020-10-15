package com.bonepl.chromaleague.league.hud.parts;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.keyboard.StaticEffect;

public class Background extends StaticEffect {
    public static final Color BACKGROUND_COLOR = new Color(10, 10, 10);

    public Background() {
        super(BACKGROUND_COLOR);
    }
}
