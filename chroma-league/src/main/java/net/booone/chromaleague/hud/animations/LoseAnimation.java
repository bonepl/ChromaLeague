package net.booone.chromaleague.hud.animations;

import net.booone.razersdk.animation.Animation;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.BreathingColor;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.sdk.ProgressiveRzKeySelector;
import net.booone.razersdk.sdk.RzKey;
import net.booone.razersdk.sdk.RzKeySelector;
import net.booone.razersdk.sdk.json.request.KeyboardEffect;

import java.util.Set;
import java.util.stream.IntStream;

public class LoseAnimation extends Animation {

    private final ProgressiveRzKeySelector progressiveRzKeySelector = new ProgressiveRzKeySelector(
            IntStream.range(0, KeyboardEffect.KEYBOARD_ROWS)
                    .mapToObj(row -> new RzKeySelector().withRow(row).asSet())
                    .toList(), 4);
    private final BreathingColor breathingColor = new BreathingColor(StaticColor.RED, new StaticColor(10, 0, 0), 50);
    private Set<RzKey> currentSet;
    int i = 0;

    @Override
    public Frame getFrame() {
        if (i == 0) {
            currentSet = progressiveRzKeySelector.getNextPart();
            i = 4;
        }
        addToFront(new SimpleFrame(currentSet, breathingColor));
        i--;
        return super.getFrame();
    }
}
