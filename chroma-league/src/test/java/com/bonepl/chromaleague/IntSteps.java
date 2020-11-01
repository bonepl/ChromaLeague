package com.bonepl.chromaleague;

public class IntSteps {
    private final int from;
    private final int to;
    private final int step;
    private int current;
    private int direction = 1;

    public IntSteps(int from, int to, int step) {
        this.from = from;
        this.to = to;
        this.step = step;
        current = from;
    }

    public int nextInt() {
        int toReturn = current;
        final int nextIncrement = step * direction;
        if (from < to) {
            if (current + nextIncrement > to || current + nextIncrement < from) {
                direction = Math.negateExact(direction);
            }
        } else {
            if (current + nextIncrement < to || current + nextIncrement > from) {
                direction = Math.negateExact(direction);
            }
        }
        current += step * direction;
        return toReturn;
    }
}
