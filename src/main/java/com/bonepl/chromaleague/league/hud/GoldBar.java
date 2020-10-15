package com.bonepl.chromaleague.league.hud;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.keyboard.ProgressBarEffect;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.Arrays;
import java.util.List;

import static com.bonepl.chromaleague.razer.sdk.RzKey.*;

public class GoldBar extends ProgressBarEffect {
    public static final int GOLD_FULL = 3000;
    public static List<RzKey> GOLD_BAR_KEYS = Arrays.asList(RZKEY_NUMPAD_DECIMAL, RZKEY_NUMPAD0,
            RZKEY_NUMPAD2, RZKEY_NUMPAD_ENTER, RZKEY_NUMPAD3, RZKEY_NUMPAD5, RZKEY_NUMPAD1,
            RZKEY_NUMPAD6, RZKEY_NUMPAD8, RZKEY_NUMPAD4, RZKEY_NUMPAD_ADD, RZKEY_NUMPAD9,
            RZKEY_NUMPAD_DIVIDE, RZKEY_NUMPAD7, RZKEY_NUMPAD_MULTIPLY, RZKEY_NUMLOCK, RZKEY_NUMPAD_SUBTRACT);

    public GoldBar(int goldPercentage) {
        super(GOLD_BAR_KEYS, goldPercentage, Color.YELLOW);
    }
}
