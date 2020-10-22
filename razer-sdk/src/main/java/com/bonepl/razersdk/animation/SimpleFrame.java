package com.bonepl.razersdk.animation;

import com.bonepl.razersdk.sdk.RzKey;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SimpleFrame implements IFrame {
    private final Map<RzKey, Color> keysToColors = new EnumMap<>(RzKey.class);
    private boolean hasFrame = true;

    public SimpleFrame() {
    }

    public SimpleFrame(Color color) {
        Arrays.stream(RzKey.values()).forEach(rzKey -> keysToColors.put(rzKey, color));
    }

    public SimpleFrame(RzKey rzKey, Color color) {
        keysToColors.put(rzKey, color);
    }

    public SimpleFrame(List<RzKey> rzKeys, Color color) {
        rzKeys.forEach(rzKey -> keysToColors.put(rzKey, color));
    }

    public SimpleFrame(Map<RzKey, Color> keysToColors) {
        this.keysToColors.putAll(keysToColors);
    }

    @Override
    public boolean hasFrame() {
        return hasFrame;
    }

    @Override
    public Frame getFrame() {
        hasFrame = false;
        return new Frame(keysToColors);
    }
}
