package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.LayeredFrame;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.Arrays;
import java.util.List;

public class AssistKillingSpreeBar extends LayeredFrame {
    private static final List<RzKey> KILLING_SPREE_BAR =
            Arrays.asList(RzKey.RZKEY_M, RzKey.RZKEY_COMA, RzKey.RZKEY_DOT,
                    RzKey.RZKEY_K, RzKey.RZKEY_L, RzKey.RZKEY_SEMICOLON,
                    RzKey.RZKEY_O, RzKey.RZKEY_P, RzKey.RZKEY_SQUARE_BRACKET_LEFT);

    public AssistKillingSpreeBar() {
        addFrame(new SimpleFrame(KILLING_SPREE_BAR.subList(0, computeAssistsIndex()), Color.YELLOW));
        addFrame(new SimpleFrame(KILLING_SPREE_BAR.subList(0, computeKillsIndex()), Color.RED));
    }

    private static int computeAssistsIndex() {
        final int activePlayerAssistSpree = GameStateHelper.getActivePlayerAssistSpree();
        return Math.min(activePlayerAssistSpree, KILLING_SPREE_BAR.size());
    }

    private static int computeKillsIndex() {
        final int activePlayerKillingSpree = GameStateHelper.getActivePlayerKillingSpree();
        return Math.min(activePlayerKillingSpree, KILLING_SPREE_BAR.size());
    }
}
