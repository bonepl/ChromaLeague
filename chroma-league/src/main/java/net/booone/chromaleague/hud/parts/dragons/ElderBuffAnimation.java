package net.booone.chromaleague.hud.parts.dragons;

import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.Frame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.sdk.RzKeyJoiner;

import java.util.List;

import static net.booone.razersdk.sdk.RzKey.*;

public class ElderBuffAnimation extends AnimatedFrame {

    @Override
    public Frame getFrame() {
        if (!hasFrame()) {
            extendAnimation();
        }
        return super.getFrame();
    }

    private void extendAnimation() {
        addAnimationFrame(2, new SimpleFrame(new RzKeyJoiner()
                .with(KilledDragonsBar.FOURTH_DRAGON_ROW).with(RZKEY_RALT)
                .with(KilledDragonsBar.SOUL_BAR)
                .with(RZKEY_LEFT, RZKEY_DOWN, RZKEY_RIGHT).join(), StaticColor.WHITE));
        addAnimationFrame(2, new SimpleFrame(List.of(RZKEY_RSHIFT,
                RZKEY_UP, RZKEY_SLASH), StaticColor.WHITE));
        addAnimationFrame(2, new SimpleFrame(List.of(RZKEY_ENTER, RZKEY_APOSTROPHE), StaticColor.WHITE));
        addAnimationFrame(2, new SimpleFrame(List.of(RZKEY_BACKSLASH, RZKEY_SQUARE_BRACKET_RIGHT), StaticColor.WHITE));
        addAnimationFrame(20, new SimpleFrame());
    }
}
