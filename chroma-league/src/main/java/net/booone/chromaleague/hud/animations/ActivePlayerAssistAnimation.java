package net.booone.chromaleague.hud.animations;

import net.booone.chromaleague.hud.parts.AssistKillingSpreeBar;
import net.booone.chromaleague.hud.parts.Background;
import net.booone.razersdk.animation.AnimatedFrame;
import net.booone.razersdk.animation.LayeredFrame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.sdk.RzKey;

import java.util.List;

import static net.booone.razersdk.sdk.RzKey.*;

public class ActivePlayerAssistAnimation extends AnimatedFrame {

    public ActivePlayerAssistAnimation() {
        for (int i = 0; i < 4; i++) {
            addAnimationFrame(2, withBackground(List.of(RZKEY_O, RZKEY_L, RZKEY_DOT)));
            addAnimationFrame(2, withBackground(List.of(RZKEY_K, RZKEY_L, RZKEY_SEMICOLON)));
            addAnimationFrame(2, withBackground(List.of(RZKEY_M, RZKEY_L, RZKEY_SQUARE_BRACKET_LEFT)));
            addAnimationFrame(2, withBackground(List.of(RZKEY_COMA, RZKEY_L, RZKEY_P)));
        }
    }

    private static LayeredFrame withBackground(List<RzKey> rzKeys) {
        final LayeredFrame layeredFrame = new LayeredFrame();
        layeredFrame.addFrame(new SimpleFrame(AssistKillingSpreeBar.getKillingSpreeBar(), Background.getCurrentBackgroundColor()));
        layeredFrame.addFrame(new SimpleFrame(rzKeys, StaticColor.YELLOW));
        return layeredFrame;
    }
}
