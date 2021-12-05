package com.bonepl.razersdk.sdk;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bonepl.razersdk.sdk.RzKeyArray.keyAt;

public final class CircularRzKeySelector {
    private final int thickness;
    private int currentStep = 0;
    private List<Set<RzKey>> layers;

    public CircularRzKeySelector(RzKey startingKey) {
        this(startingKey, 1);
    }

    public CircularRzKeySelector(RzKey startingKey, int thickness) {
        this.thickness = thickness;
        layers = buildLayers(startingKey);
    }

    private List<Set<RzKey>> buildLayers(RzKey startingKey) {
        List<Set<RzKey>> list = new LinkedList<>();
        list.add(Collections.singleton(startingKey));
        for (int i = 0; i < 30; i++) {
            Set<RzKey> previousLayer = list.get(i);
            Set<RzKey> newLayer = previousLayer.stream().flatMap(rzKey -> Stream.of(
                    keyAt(rzKey.getRow() - 1, rzKey.getColumn()),
                    keyAt(rzKey.getRow() - 1, rzKey.getColumn() + 1),
                    keyAt(rzKey.getRow() + 1, rzKey.getColumn()),
                    keyAt(rzKey.getRow() + 1, rzKey.getColumn() + 1),
                    keyAt(rzKey.getRow(), rzKey.getColumn() - 1),
                    keyAt(rzKey.getRow(), rzKey.getColumn() + 1)
            ).filter(Optional::isPresent).map(Optional::get)).collect(Collectors.toSet());
            newLayer.removeAll(list.stream().flatMap(Collection::stream).collect(Collectors.toSet()));
            list.add(newLayer);
        }
        return list;
    }

    public Set<RzKey> getNextLayer() {
        if (currentStep != layers.size() - 1) {
            currentStep++;
        }

        return layers.stream()
                .limit(currentStep)
                .skip(countSkippedLayers(currentStep, thickness))
                .flatMap(Set::stream).collect(Collectors.toSet());
    }

    private int countSkippedLayers(int currentStep, int thickness) {
        if (currentStep > thickness) {
            return currentStep - thickness;
        }
        return 0;
    }
}

