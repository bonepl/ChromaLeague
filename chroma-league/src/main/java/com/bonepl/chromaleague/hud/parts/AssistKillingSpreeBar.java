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
            Arrays.asList(RzKey.RZKEY_OEM_9, RzKey.RZKEY_OEM_10, RzKey.RZKEY_OEM_11,
                    RzKey.RZKEY_L, RzKey.RZKEY_OEM_7, RzKey.RZKEY_OEM_8,
                    RzKey.RZKEY_P, RzKey.RZKEY_OEM_4, RzKey.RZKEY_OEM_5);

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
