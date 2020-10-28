package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.hud.parts.ProgressBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.sdk.RzKey;
import com.bonepl.razersdk.sdk.RzKeySelector;

import java.util.Collections;
import java.util.List;

import static com.bonepl.chromaleague.state.GameStateHelper.getResourceType;
import static com.bonepl.razersdk.sdk.RzKey.RZKEY_BACKSPACE;
import static com.bonepl.razersdk.sdk.RzKey.RZKEY_TILDE;

/**
 * vladimir uses resource type: GNARFURY
 * shyvana uses resource type: DRAGONFURY
 */
public class ManaBar extends ProgressBar {
    private static final List<RzKey> RESOURCE_BAR_KEYS = new RzKeySelector()
            .withRowOf(RZKEY_TILDE)
            .withColumnBetween(RZKEY_TILDE, RZKEY_BACKSPACE)
            .sortedByColumn()
            .asList();

    public ManaBar() {
        super(RESOURCE_BAR_KEYS, GameStateHelper.getResourcePercentage(), getResourceType().getColor());
    }

    public static List<RzKey> getResourceBarKeys() {
        return Collections.unmodifiableList(RESOURCE_BAR_KEYS);
    }
}
