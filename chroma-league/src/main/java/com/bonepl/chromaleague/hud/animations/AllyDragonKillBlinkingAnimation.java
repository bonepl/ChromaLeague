package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.StaticColor;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.List;

import static com.bonepl.chromaleague.hud.PredefinedKeySets.BLACKWIDOW_FUNCTIONAL;

public abstract class AllyDragonKillBlinkingAnimation extends DragonKillBlinkingAnimation {

    public AllyDragonKillBlinkingAnimation(List<RzKey> rzKeys, DragonType dragonType) {
        super(BLACKWIDOW_FUNCTIONAL, dragonType);
    }
}
