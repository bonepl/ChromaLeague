package com.bonepl.razersdk.sdk;

import java.util.*;

public final class RzKeyJoiner {
    private final EnumSet<RzKey> rzKeys = EnumSet.noneOf(RzKey.class);

    public RzKeyJoiner with(RzKey... rzKey) {
        if (rzKey.length != 0) {
            rzKeys.addAll(List.of(rzKey));
        }
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
