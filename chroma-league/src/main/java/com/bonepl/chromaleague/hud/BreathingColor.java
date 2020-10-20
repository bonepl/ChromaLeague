package com.bonepl.chromaleague.hud;

import com.bonepl.razersdk.animation.Color;

public class BreathingColor {
    private final int maxRed;
    private final int maxGreen;
    private final int maxBlue;
    private final int steps;

    private int direction = 1;
    private int currentStep = 0;

    public BreathingColor(Color color) {
        this(color, 20);
    }

    public BreathingColor(Color color, int steps) {
        this.maxRed = color.getRed();
        this.maxGreen = color.getGreen();
        this.maxBlue = color.getBlue();
        this.steps = steps;
    }

    public Color getNextColor() {
        if (currentStep + direction > steps || currentStep + direction < 0) {
            direction = Math.negateExact(direction);
        }

        currentStep += direction;
        if(currentStep == 0) {
            return new Color(2,2,2);
        }

        int red = currentStep * getRedStep();
        int green = currentStep * getGreenStep();
        int blue = currentStep * getBlueStep();
        return new Color(red, green, blue);
    }

    private int getRedStep() {
        return maxRed / steps;
    }

    private int getGreenStep() {
        return maxGreen / steps;
    }

    private int getBlueStep() {
        return maxBlue / steps;
    }
}
