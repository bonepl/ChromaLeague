package net.booone.chromaleague.hud.animations;

import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.sdk.ProgressiveRzKeySelector;
import net.booone.razersdk.sdk.RzKey;
import net.booone.razersdk.sdk.RzKeySelector;
import net.booone.razersdk.sdk.json.request.KeyboardEffect;

import java.util.stream.IntStream;

public class LoadingAnimation extends AnimatedFrame {
    private static final Color LOADING_COLOR = StaticColor.GREEN;
    private static final ProgressiveRzKeySelector progressiveRzKeySelector = new ProgressiveRzKeySelector(IntStream.concat(
                    IntStream.range(0, KeyboardEffect.KEYBOARD_COLUMNS),
                    IntStream.range(0, KeyboardEffect.KEYBOARD_COLUMNS)
                            .map(i -> KeyboardEffect.KEYBOARD_COLUMNS - i - 1))
            .mapToObj(col -> new RzKeySelector().withColumn(col).withRowBetween(RzKey.RZKEY_ESC, RzKey.RZKEY_LCTRL).asSet())
            .toList(), 2);

    @Override
    public Frame getFrame() {
        addAnimationFrame(new SimpleFrame(progressiveRzKeySelector.getNextPart(), LOADING_COLOR));
        return super.getFrame();
    }
}
