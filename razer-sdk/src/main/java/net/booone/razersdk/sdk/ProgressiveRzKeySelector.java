package net.booone.razersdk.sdk;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class ProgressiveRzKeySelector {
    private final int length;
    private final List<Set<RzKey>> parts;
    private int currentStep = 0;

    public ProgressiveRzKeySelector(List<Set<RzKey>> parts) {
        this(parts, 1);
    }

    public ProgressiveRzKeySelector(List<Set<RzKey>> parts, int length) {
        if (parts == null || parts.isEmpty()) {
            throw new IllegalArgumentException("There must be at least one part");
        }

        if (length <= 0) {
            throw new IllegalArgumentException("Length must be a positive number");
        } else if (length > parts.size()) {
            throw new IllegalArgumentException("Length cannot be larger than number of parts");
        }

        this.parts = parts;
        this.length = length;
    }

    public Set<RzKey> getNextPart() {
        int span = length - 1;
        if (currentStep == parts.size() + span) {
            currentStep = 0;
        }
        Set<RzKey> returnedPart;
        if (currentStep - span <= 0) {
            returnedPart = joinSets(0, currentStep);
        } else if (currentStep >= parts.size() - 1) {
            returnedPart = joinSets(currentStep - span, parts.size() - 1);
        } else {
            returnedPart = joinSets(currentStep - span, currentStep);
        }
        currentStep++;
        return returnedPart;
    }

    public int getTotalSteps() {
        return parts.size() + length - 1;
    }

    private Set<RzKey> joinSets(int startIndex, int endIndex) {
        return parts.subList(startIndex, endIndex + 1)
                .stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public static class Builder {
        private final List<Set<RzKey>> parts = new LinkedList<>();
        private int length = 1;

        public Builder addPart(Collection<RzKey> rzKeys) {
            parts.add(Set.copyOf(rzKeys));
            return this;
        }

        public Builder addPart(RzKey... rzKeys) {
            return addPart(Set.of(rzKeys));
        }

        public Builder withLength(int length) {
            this.length = length;
            return this;
        }

        public ProgressiveRzKeySelector build() {
            return new ProgressiveRzKeySelector(parts, length);
        }
    }
}
