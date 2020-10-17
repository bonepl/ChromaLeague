package com.bonepl.razersdk.effects.keyboard;

import com.bonepl.razersdk.RazerSDKClient;
import com.bonepl.razersdk.effects.Color;
import com.bonepl.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class ProgressBarTest {

    public static final List<RzKey> BLACKWIDOW_FIRST_ROW = Arrays.asList(RzKey.RZKEY_ESC, RzKey.RZKEY_F1, RzKey.RZKEY_F2, RzKey.RZKEY_F3, RzKey.RZKEY_F4,
            RzKey.RZKEY_F5, RzKey.RZKEY_F6, RzKey.RZKEY_F7, RzKey.RZKEY_F8, RzKey.RZKEY_F9,
            RzKey.RZKEY_F10, RzKey.RZKEY_F11, RzKey.RZKEY_F12, RzKey.RZKEY_PRINTSCREEN,
            RzKey.RZKEY_SCROLL, RzKey.RZKEY_PAUSE);

    @Test
    void testProgressBarEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i <= 100; i += 5) {
                razerSDKClient.createKeyboardEffect(new ProgressBar(BLACKWIDOW_FIRST_ROW, i, Color.GREEN));
                Thread.sleep(100);
            }
            for (int i = 100; i >= 0; i -= 5) {
                razerSDKClient.createKeyboardEffect(new ProgressBar(BLACKWIDOW_FIRST_ROW, i, Color.GREEN));
                Thread.sleep(100);
            }
        }
    }
}