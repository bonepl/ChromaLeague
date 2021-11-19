package com.bonepl.razersdk.color;

import java.util.LinkedList;
import java.util.List;

public class MultiTransitionColor implements Color {
    private final List<StaticColor> colorsList;
    private int i = 0;

    private MultiTransitionColor(List<StaticColor> colorsList) {
        this.colorsList = colorsList;
    }

    @Override
    public StaticColor getColor() {
        if (i == colorsList.size()) {
            i = 0;
        }
        return colorsList.get(i++);
    }

    public int getTotalTransitions() {
        return colorsList.size();
    }

    public static class Builder {
        private final LinkedList<StaticColor> builderList = new LinkedList<>();
        private int loopedSteps;

        public Builder(StaticColor startingColor) {
            builderList.add(startingColor);
        }

        public Builder addTransition(StaticColor toColor, int steps) {
            if (steps < 2) {
                throw new IllegalArgumentException("Minimum amount of steps in transition color is 2, requested: " + steps);
            }
            TransitionColor transitionColor = new TransitionColor(builderList.getLast(), toColor, steps);
            //skip first color as it is already on list
            transitionColor.getColor();
            while (!transitionColor.transitionFinished()) {
                builderList.add(transitionColor.getColor());
            }
            return this;
        }

        public Builder looped(int loopedSteps) {
            this.loopedSteps = loopedSteps;
            return this;
        }

        public MultiTransitionColor build() {
            if (loopedSteps != 0) {
                addTransition(builderList.getFirst(), loopedSteps);
                builderList.removeLast();
            }
            return new MultiTransitionColor(builderList);
        }
    }
}
