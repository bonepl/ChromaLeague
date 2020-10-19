package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.effects.Color;

import static com.bonepl.chromaleague.hud.PredefinedKeySets.BLACKWIDOW_FUNCTIONAL;

public class EnemyHeraldKillAnimation extends StaticPartialBlinkingAnimation {
    public EnemyHeraldKillAnimation() {
        super(BLACKWIDOW_FUNCTIONAL, 8, Color.PURPLE);
    }
}
