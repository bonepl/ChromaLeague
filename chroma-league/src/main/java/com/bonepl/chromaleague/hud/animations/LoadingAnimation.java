package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.hud.parts.Background;
import com.bonepl.razersdk.animation.*;
import com.bonepl.razersdk.sdk.RzKey;
import com.bonepl.razersdk.sdk.RzKeySelector;
import com.bonepl.razersdk.sdk.json.request.KeyboardEffect;

import java.util.Set;

public class LoadingAnimation extends AnimatedFrame {
    private static final Color LOADING_COLOR = Color.GREEN;
    private int currentColumn;
    private int direction;

    public LoadingAnimation() {
        currentColumn = 0;
        direction = 1;
    }

    @Override
    public Frame getFrame() {
        final LayeredFrame nextFrame = getNextFrame(currentColumn);
        if (currentColumn + direction < 0 ||
                currentColumn + direction > KeyboardEffect.KEYBOARD_COLUMNS) {
            direction = Math.negateExact(direction);
        }
        currentColumn += direction;
        addAnimationFrame(nextFrame);
        return super.getFrame();
    }

    private static LayeredFrame getNextFrame(int column) {
        final LayeredFrame layeredFrame = new LayeredFrame();
        layeredFrame.addFrame(new SimpleFrame(Background.BACKGROUND_COLOR));
        final Set<RzKey> rzKeys = new RzKeySelector()
                .withColumn(column)
                .withAnyRow().asSet();
        layeredFrame.addFrame(new SimpleFrame(rzKeys, LOADING_COLOR));
        return layeredFrame;
    }
}
