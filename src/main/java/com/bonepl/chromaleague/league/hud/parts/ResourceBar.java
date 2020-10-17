package com.bonepl.chromaleague.league.hud.parts;

import com.bonepl.chromaleague.league.GameStateHelper;
import com.bonepl.chromaleague.razer.effects.keyboard.ProgressBar;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.Arrays;
import java.util.List;

import static com.bonepl.chromaleague.league.GameStateHelper.getResourceType;
import static com.bonepl.chromaleague.razer.sdk.RzKey.*;

/**
 * vladimir uses resource type: GNARFURY
 * shyvana uses resource type: DRAGONFURY
 */
public class ResourceBar extends ProgressBar {
    public static List<RzKey> RESOURCE_BAR_KEYS = Arrays.asList(RZKEY_OEM_1, RZKEY_1, RZKEY_2, RZKEY_3, RZKEY_4, RZKEY_5,
            RZKEY_6, RZKEY_7, RZKEY_8, RZKEY_9, RZKEY_0, RZKEY_OEM_2, RZKEY_OEM_3, RZKEY_BACKSPACE);

    public ResourceBar() {
        super(RESOURCE_BAR_KEYS, GameStateHelper.getResourcePercentage(), getResourceType().getColor());
    }
}
