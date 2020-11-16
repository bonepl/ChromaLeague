package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.animation.AnimatedFrame;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;
import com.bonepl.razersdk.sdk.RzKeySelector;
import com.bonepl.razersdk.sdk.json.request.KeyboardEffect;

import java.util.List;

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
        final SimpleFrame nextFrame = getNextFrame(currentColumn);
        if (currentColumn + direction < 0 ||
                currentColumn + direction > KeyboardEffect.KEYBOARD_COLUMNS) {
            direction = Math.negateExact(direction);
        }
        currentColumn += direction;
        addAnimationFrame(nextFrame);
        return super.getFrame();
    }

    private static SimpleFrame getNextFrame(int column) {
        final List<RzKey> rzKeys = new RzKeySelector()
                .withColumn(column)
                .withAnyRow().asList();
        return new SimpleFrame(rzKeys, LOADING_COLOR);
    }
}
