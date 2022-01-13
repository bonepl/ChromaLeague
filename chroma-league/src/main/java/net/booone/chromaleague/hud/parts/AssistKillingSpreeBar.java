package net.booone.chromaleague.hud.parts;

import net.booone.chromaleague.state.GameStateHelper;
import net.booone.razersdk.animation.LayeredFrame;
import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.sdk.RzKey;

import static net.booone.razersdk.sdk.RzKey.*;

import java.util.List;

public class AssistKillingSpreeBar extends LayeredFrame {
    private static final List<RzKey> KILLING_SPREE_BAR =
            List.of(RZKEY_M, RZKEY_COMA, RZKEY_DOT,
                    RZKEY_K, RZKEY_L, RZKEY_SEMICOLON,
                    RZKEY_O, RZKEY_P, RZKEY_SQUARE_BRACKET_LEFT);

    public AssistKillingSpreeBar() {
        addFrame(new SimpleFrame(KILLING_SPREE_BAR.subList(0, computeAssistsIndex()), StaticColor.YELLOW));
        addFrame(new SimpleFrame(KILLING_SPREE_BAR.subList(0, computeKillsIndex()), StaticColor.RED));
    }

    private static int computeAssistsIndex() {
        return Math.min(GameStateHelper.getActivePlayerAssistSpree(), KILLING_SPREE_BAR.size());
    }

    private static int computeKillsIndex() {
        return Math.min(GameStateHelper.getActivePlayerKillingSpree(), KILLING_SPREE_BAR.size());
    }

    public static List<RzKey> getKillingSpreeBar() {
        return KILLING_SPREE_BAR;
    }
}
