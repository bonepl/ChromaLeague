package com.bonepl.chromaleague.hud;

import com.bonepl.razersdk.sdk.RzKey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

public class KeysJoiner {
    private final EnumSet<RzKey> rzKeys = EnumSet.noneOf(RzKey.class);

    public KeysJoiner with(Collection<RzKey> collection) {
        rzKeys.addAll(collection);
        return this;
    }

    public List<RzKey> join() {
        return new ArrayList<>(rzKeys);
    }
}
