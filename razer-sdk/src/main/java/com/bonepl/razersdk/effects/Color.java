package com.bonepl.razersdk.effects;

public class Color {

    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color YELLOW = new Color(255, 255, 0);
    public static final Color CYAN = new Color(0, 255, 255);
    public static final Color BROWN = new Color(139, 69, 19);
    public static final Color PURPLE = new Color(238, 130, 238);
    public static final Color ORANGE = new Color(255, 165, 0);

    private final int red;
    private final int green;
    private final int blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getSDKColorRef() {
        return blue << 16 | green << 8 | red;
    }
}
