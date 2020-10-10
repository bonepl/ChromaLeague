package com.bonepl.chromaleague.razer.sdk.effects;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class ProgressBarKeyboardEffectTest {
    @Test
    void testProgressBarEffect() throws InterruptedException {
        //given
        List<RzKey> blackWidowFirstRow = Arrays.asList(RzKey.RZKEY_ESC, RzKey.RZKEY_F1, RzKey.RZKEY_F2, RzKey.RZKEY_F3, RzKey.RZKEY_F4,
                RzKey.RZKEY_F5, RzKey.RZKEY_F6, RzKey.RZKEY_F7, RzKey.RZKEY_F8, RzKey.RZKEY_F9,
                RzKey.RZKEY_F10, RzKey.RZKEY_F11, RzKey.RZKEY_F12, RzKey.RZKEY_PRINTSCREEN,
                RzKey.RZKEY_SCROLL, RzKey.RZKEY_PAUSE);

        //then
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i <= 100; i += 5) {
                razerSDKClient.createKeyboardEffect(
                        new ProgressBarKeyboardEffect(blackWidowFirstRow, i, Color.GREEN));
                Thread.sleep(100);
            }
            for (int i = 100; i >= 0; i -= 5) {
                razerSDKClient.createKeyboardEffect(
                        new ProgressBarKeyboardEffect(blackWidowFirstRow, i, Color.GREEN));
                Thread.sleep(100);
            }
        }
    }
}