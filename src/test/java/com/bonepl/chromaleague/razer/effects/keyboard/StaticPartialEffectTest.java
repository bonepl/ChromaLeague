package com.bonepl.chromaleague.razer.effects.keyboard;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.Color;
import com.bonepl.chromaleague.razer.effects.animation.Frame;
import com.bonepl.chromaleague.razer.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.bonepl.chromaleague.razer.sdk.RzKey.*;

class StaticPartialEffectTest {
    List<RzKey> smiley = Arrays.asList(RZKEY_W, RZKEY_3, RZKEY_4, RZKEY_R,
            RZKEY_I, RZKEY_9, RZKEY_0, RZKEY_P,
            RZKEY_X, RZKEY_SPACE, RZKEY_OEM_9);

    @Test
    void testPartialStaticEffect() throws InterruptedException {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            razerSDKClient.createKeyboardEffect(new Frame(smiley, Color.YELLOW));
            Thread.sleep(100);
        }
    }
}