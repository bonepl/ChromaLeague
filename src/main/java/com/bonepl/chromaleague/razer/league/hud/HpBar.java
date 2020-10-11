package com.bonepl.chromaleague.razer.league.hud;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.keyboard.ProgressBarEffect;
import com.bonepl.chromaleague.razer.league.json.activeplayer.ActivePlayerThread;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.Arrays;
import java.util.List;

import static com.bonepl.chromaleague.razer.sdk.RzKey.*;

public class HpBar extends ProgressBarEffect {
    public static List<RzKey> HP_BAR_KEYS = Arrays.asList(RZKEY_ESC, RZKEY_F1, RZKEY_F2, RZKEY_F3, RZKEY_F4, RZKEY_F5,
            RZKEY_F6, RZKEY_F7, RZKEY_F8, RZKEY_F9, RZKEY_F10, RZKEY_F11, RZKEY_F12);

    public HpBar(ActivePlayerThread activePlayerThread) {
        super(HP_BAR_KEYS, activePlayerThread.getHpPercentage(), Color.GREEN);
    }
}
