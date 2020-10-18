package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.GameStateHelper;
import com.bonepl.razersdk.effects.keyboard.ProgressBar;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.List;

import static com.bonepl.chromaleague.GameStateHelper.getResourceType;
import static com.bonepl.chromaleague.hud.PredefinedKeySets.BLACKWIDOW_SECOND_ROW;

/**
 * vladimir uses resource type: GNARFURY
 * shyvana uses resource type: DRAGONFURY
 */
public class ResourceBar extends ProgressBar {
    public static List<RzKey> RESOURCE_BAR_KEYS = BLACKWIDOW_SECOND_ROW.subList(1, 14);

    public ResourceBar() {
        super(RESOURCE_BAR_KEYS, GameStateHelper.getResourcePercentage(), getResourceType().getColor());
    }
}
