package com.bonepl.chromaleague.league.hud.parts;

import com.bonepl.chromaleague.razer.effects.keyboard.LayeredCustomEffect;

import static com.bonepl.chromaleague.league.GameStateHelper.*;

public class MainHud extends LayeredCustomEffect {

    public MainHud() {
        addCustomKeyboardEffect(new Background());
        addCustomKeyboardEffect(new HpBar(getHpPercentage()));
        addCustomKeyboardEffect(new ResourceBar(getResourcePercentage(), getResourceType()));
        addCustomKeyboardEffect(new GoldBar(getGoldPercentage()));
    }
}
