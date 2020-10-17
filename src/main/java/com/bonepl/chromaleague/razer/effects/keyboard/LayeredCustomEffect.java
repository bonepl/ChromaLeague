package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.animation.AnimatedFrame;
import com.bonepl.chromaleague.razer.effects.animation.Animation;
import com.bonepl.chromaleague.razer.effects.animation.Frame;

public class LayeredCustomEffect extends CustomEffect {

    public void addCustomKeyboardEffect(Frame frame) {
        addCustomKeyboardEffect(frame.toCustomEffect());
    }

    public void addCustomKeyboardEffect(AnimatedFrame animatedFrame) {
        addCustomKeyboardEffect(animatedFrame.getFrame().toCustomEffect());
    }

    public void addCustomKeyboardEffect(Animation animation) {
        addCustomKeyboardEffect(animation.getNextAnimatedFrame().toCustomEffect());
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
