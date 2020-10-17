package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.GameStateHelper;
import com.bonepl.razersdk.effects.Color;
import com.bonepl.razersdk.effects.keyboard.ProgressBar;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.Arrays;
import java.util.List;

import static com.bonepl.razersdk.sdk.RzKey.*;

public class HpBar extends ProgressBar {
    public static List<RzKey> HP_BAR_KEYS = Arrays.asList(RZKEY_ESC, RZKEY_F1, RZKEY_F2, RZKEY_F3, RZKEY_F4, RZKEY_F5,
            RZKEY_F6, RZKEY_F7, RZKEY_F8, RZKEY_F9, RZKEY_F10, RZKEY_F11, RZKEY_F12);

    public HpBar() {
        super(HP_BAR_KEYS, GameStateHelper.getHpPercentage(), Color.GREEN);
    }
}
