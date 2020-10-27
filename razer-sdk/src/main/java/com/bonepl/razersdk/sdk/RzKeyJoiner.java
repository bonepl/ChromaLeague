package com.bonepl.razersdk.sdk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

public class RzKeyJoiner {
    private final EnumSet<RzKey> rzKeys = EnumSet.noneOf(RzKey.class);

    public RzKeyJoiner with(RzKey rzKey) {
        rzKeys.add(rzKey);
        return this;
    }

    public RzKeyJoiner with(Collection<RzKey> collection) {
        rzKeys.addAll(collection);
        return this;
    }

    public RzKeyJoiner with(RzKeySelector rzKeySelector) {
        rzKeys.addAll(rzKeySelector.asList());
        return this;
    }

    public List<RzKey> join() {
        return new ArrayList<>(rzKeys);
    }
}
