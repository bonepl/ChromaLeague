package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.LayeredFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.color.StaticColor;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.List;

import static com.bonepl.razersdk.sdk.RzKey.*;

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
