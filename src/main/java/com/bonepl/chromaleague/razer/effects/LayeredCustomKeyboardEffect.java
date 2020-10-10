package com.bonepl.chromaleague.razer.effects;

public class LayeredCustomKeyboardEffect extends CustomKeyboardEffect {

    public void addCustomKeyboardEffect(CustomKeyboardEffect customKeyboardEffect) {
        for (int i = 0; i < colors.length; i++) {
            if (customKeyboardEffect.colors[i] != Color.BLACK.getSDKColorRef()) {
                colors[i] = customKeyboardEffect.colors[i];
            }
        }
    }

    public LayeredCustomKeyboardEffect withCustomKeyboardEffect(CustomKeyboardEffect customKeyboardEffect) {
        addCustomKeyboardEffect(customKeyboardEffect);
        return this;
    }

}
