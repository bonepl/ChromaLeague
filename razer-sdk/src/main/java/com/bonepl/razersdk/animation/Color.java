package com.bonepl.razersdk.animation;

public class Color {
    public static final Color NONE = new Color(0, 0, 0);

    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color AIR = new Color(120, 150, 150);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color YELLOW = new Color(255, 255, 0);
    public static final Color CYAN = new Color(0, 255, 255);
    public static final Color OCEAN = new Color(0, 210, 70);
    public static final Color BROWN = new Color(130, 50, 0);
    public static final Color PURPLE = new Color(238, 130, 238);
    public static final Color ORANGE = new Color(255, 165, 0);

    private static final int BLUE_BIT_POS = 16;
    private static final int GREEN_BIT_POS = 8;

    private final int red;
    private final int green;
    private final int blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getSDKColorRef() {
        return blue << BLUE_BIT_POS | green << GREEN_BIT_POS | red;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
