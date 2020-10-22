package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.GameStateHelper;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.Arrays;
import java.util.List;

public class KillingSpreeBar extends ProgressBar {

    private static final List<RzKey> KILLING_SPREE_BAR =
            Arrays.asList(RzKey.RZKEY_OEM_9, RzKey.RZKEY_OEM_10, RzKey.RZKEY_OEM_11,
                    RzKey.RZKEY_L, RzKey.RZKEY_OEM_7, RzKey.RZKEY_OEM_8,
                    RzKey.RZKEY_P, RzKey.RZKEY_OEM_4, RzKey.RZKEY_OEM_5);

    public KillingSpreeBar() {
        super(KILLING_SPREE_BAR, computePercent(), Color.RED);
    }

    private static int computePercent() {
        return GameStateHelper.getActivePlayerKillingSpree() * 100 / KILLING_SPREE_BAR.size() + 1;
    }
}
