package com.bonepl.chromaleague.razer.effects.animation;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.sdk.RzKey;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.bonepl.chromaleague.razer.sdk.RzKey.*;

public class GoldAnimation extends Animation {
    private static final Random random = new Random();
    private static final List<RzKey> FIRST_NUMPAD_COLUMN = Arrays.asList(RZKEY_NUMLOCK, RZKEY_NUMPAD7, RZKEY_NUMPAD4, RZKEY_NUMPAD1, RZKEY_NUMPAD0);
    private static final List<RzKey> SECOND_NUMPAD_COLUMN = Arrays.asList(RZKEY_NUMPAD_DIVIDE, RZKEY_NUMPAD8, RZKEY_NUMPAD5, RZKEY_NUMPAD2, RZKEY_NUMPAD0);
    private static final List<RzKey> THIRD_NUMPAD_COLUMN = Arrays.asList(RZKEY_NUMPAD_MULTIPLY, RZKEY_NUMPAD9, RZKEY_NUMPAD6, RZKEY_NUMPAD3, RZKEY_NUMPAD_DECIMAL);
    private static final List<RzKey> BOTTOM_NUMPAD = Arrays.asList(RZKEY_NUMPAD0, RZKEY_NUMPAD_DECIMAL, RZKEY_NUMPAD_ENTER, RZKEY_NUMPAD_ADD, RZKEY_NUMPAD_SUBTRACT);

    public GoldAnimation() {
        addToFront(new StaticFrames(new Frame(BOTTOM_NUMPAD, Color.BROWN)));
    }

    public void spawnGold() {
        final int i = random.nextInt(3);
        switch (i) {
            case 0 -> addToBack(fallingCoin(FIRST_NUMPAD_COLUMN));
            case 1 -> addToBack(fallingCoin(SECOND_NUMPAD_COLUMN));
            case 2 -> addToBack(fallingCoin(THIRD_NUMPAD_COLUMN));
        }
    }

    private Frames fallingCoin(List<RzKey> rzKeys) {
        final MotionFrames motionFrames = new MotionFrames();
        rzKeys.forEach(rzKey -> motionFrames.withAnimationFrame(new Frame(rzKey, Color.YELLOW)));
        return motionFrames;
    }
}
