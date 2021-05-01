package com.bonepl.razersdk.animation;

import java.io.Serializable;

/**
 * Class describing color in RGB format.
 * Note that Razer Chroma SDK is using BGR format encoded with {@link #getSDKColorRef()}
 */
public record Color(int red, int green, int blue) implements Serializable {
    public static final Color NONE = new Color(0, 0, 0);

    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color YELLOW = new Color(255, 255, 0);
    public static final Color CYAN = new Color(0, 255, 255);
    public static final Color BROWN = new Color(130, 50, 0);
    public static final Color PURPLE = new Color(200, 0, 200);
    public static final Color ORANGE = new Color(255, 165, 0);
    public static final Color GRAY = new Color(100, 100, 100);

    public static final int MIN_COLOR_VALUE = 0;
    public static final int MAX_COLOR_VALUE = 255;

    private static final int GREEN_BIT_POS = 8;
    private static final int BLUE_BIT_POS = 16;

    /**
     * Create color definition in RGB
     *
     * @param red   red color (0-255)
     * @param green green color (0-255)
     * @param blue  blue color (0-255)
     * @throws IllegalArgumentException if color value is out of accepted range
     */
    public Color(int red, int green, int blue) {
        this.red = validatedColorRange("red", red);
        this.green = validatedColorRange("green", green);
        this.blue = validatedColorRange("blue", blue);
    }

    private static int validatedColorRange(String colorName, int value) {
        if (value < MIN_COLOR_VALUE || value > MAX_COLOR_VALUE) {
            throw new IllegalArgumentException(
                    String.format("%s value of provided color is out of range (expected: 0-255, actual: %d)",
                            colorName, value));
        }
        return value;
    }

    /**
     * @return Razer Chroma SDK compatible color in BGR format
     */
    public int getSDKColorRef() {
        return blue << BLUE_BIT_POS | green << GREEN_BIT_POS | red;
    }
}
