package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.sdk.RzKey;
import com.bonepl.razersdk.sdk.RzKeySelector;

import java.util.Collections;
import java.util.List;

import static com.bonepl.razersdk.sdk.RzKey.RZKEY_ESC;
import static com.bonepl.razersdk.sdk.RzKey.RZKEY_F12;

public class HpBar extends ProgressBar {
    private static final List<RzKey> HP_BAR_KEYS = new RzKeySelector()
            .withRowOf(RZKEY_ESC)
            .withColumnBetween(RZKEY_ESC, RZKEY_F12)
            .sortedByColumn()
            .asList();

    public HpBar() {
        super(HP_BAR_KEYS, GameStateHelper.getHpPercentage(), Color.GREEN);
    }

    public static List<RzKey> getHpBarKeys() {
        return Collections.unmodifiableList(HP_BAR_KEYS);
    }
}
