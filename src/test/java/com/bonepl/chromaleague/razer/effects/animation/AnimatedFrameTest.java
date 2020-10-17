package com.bonepl.chromaleague.razer.effects.animation;

import com.bonepl.chromaleague.league.hud.animations.GoldAnimation;
import com.bonepl.chromaleague.razer.RazerSDKClient;
import org.junit.jupiter.api.Test;

import static com.bonepl.chromaleague.razer.effects.Color.RED;
import static com.bonepl.chromaleague.razer.effects.Color.YELLOW;
import static com.bonepl.chromaleague.razer.sdk.RzKey.*;

class AnimatedFrameTest {
    @Test
    void testAnimation() throws InterruptedException {
        final GoldAnimation goldAnimation = new GoldAnimation();
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i < 5000; i++) {
                razerSDKClient.createKeyboardEffect(goldAnimation.getNextFrame().toCustomEffect());
                if (i % 10 == 0) {
                    goldAnimation.spawnCoin();
                }
                Thread.sleep(100);
            }
        }
    }

    private AnimatedFrame createLevelUpAnimation() {
        return new AnimatedFrame()
                .withAnimationFrame(new Frame(RZKEY_MACRO5, YELLOW))
                .withAnimationFrame(new Frame(RZKEY_MACRO4, YELLOW))
                .withAnimationFrame(new Frame(RZKEY_MACRO3, YELLOW))
                .withAnimationFrame(new Frame(RZKEY_MACRO2, YELLOW))
                .withAnimationFrame(new Frame(RZKEY_MACRO1, YELLOW));
    }

    private AnimatedFrame createLevelDownAnimation() {
        return new AnimatedFrame()
                .withAnimationFrame(new Frame(RZKEY_MACRO1, RED))
                .withAnimationFrame(new Frame(RZKEY_MACRO2, RED))
                .withAnimationFrame(new Frame(RZKEY_MACRO3, RED))
                .withAnimationFrame(new Frame(RZKEY_MACRO4, RED))
                .withAnimationFrame(new Frame(RZKEY_MACRO5, RED));
    }
}