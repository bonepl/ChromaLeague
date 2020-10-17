package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.animation.AnimatedFrame;
import com.bonepl.chromaleague.razer.effects.animation.Animation;

public class LayeredCustomEffect extends CustomEffect {

    public void addCustomKeyboardEffect(Animation animation) {
        addCustomKeyboardEffect(animation.getNextAnimatedFrame());
    }

    public void addCustomKeyboardEffect(AnimatedFrame frame) {
        addCustomKeyboardEffect(frame.getNextFrame());
    }

    public void addCustomKeyboardEffect(CustomEffect customEffect) {
        for (int i = 0; i < colors.length; i++) {
            if (customEffect.colors[i] != Color.BLACK.getSDKColorRef()) {
                colors[i] = customEffect.colors[i];
            }
        }
    }

    public LayeredCustomEffect withCustomKeyboardEffect(CustomEffect customEffect) {
        addCustomKeyboardEffect(customEffect);
        return this;
    }

}
