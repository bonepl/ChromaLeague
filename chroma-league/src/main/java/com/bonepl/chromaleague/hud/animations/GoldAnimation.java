package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.GameStateHelper;
import com.bonepl.chromaleague.hud.parts.GoldBar;
import com.bonepl.razersdk.effects.Color;
import com.bonepl.razersdk.effects.animation.AnimatedFrame;
import com.bonepl.razersdk.effects.animation.Animation;
import com.bonepl.razersdk.effects.animation.Frame;
import com.bonepl.razersdk.sdk.RzKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.bonepl.razersdk.sdk.RzKey.*;

public class GoldAnimation extends Animation {
    private static final Logger logger = LogManager.getLogger();

    private static final List<RzKey> FIRST_NUMPAD_COLUMN
            = Arrays.asList(RZKEY_NUMLOCK, RZKEY_NUMPAD7, RZKEY_NUMPAD4, RZKEY_NUMPAD1, RZKEY_NUMPAD0);
    private static final List<RzKey> SECOND_NUMPAD_COLUMN
            = Arrays.asList(RZKEY_NUMPAD_DIVIDE, RZKEY_NUMPAD8, RZKEY_NUMPAD5, RZKEY_NUMPAD2, RZKEY_NUMPAD0);
    private static final List<RzKey> THIRD_NUMPAD_COLUMN
            = Arrays.asList(RZKEY_NUMPAD_MULTIPLY, RZKEY_NUMPAD9, RZKEY_NUMPAD6, RZKEY_NUMPAD3, RZKEY_NUMPAD_DECIMAL);
    private static final List<RzKey> FOURTH_NUMPAD_COLUMN
            = Arrays.asList(RZKEY_NUMPAD_SUBTRACT, RZKEY_NUMPAD_ADD, RZKEY_NUMPAD_ENTER);

    private static final Random random = new Random();
    private static final double goldDiffToSpawnCoin = 10;
    private static final int millisecondsForGoldCount = 1000;
    private Double lastGold;
    private LocalTime lastGoldCheck;

    @Override
    public Frame getFrame() {
        addToFront(new GoldBar());
        spawnCoinIfNeeded();
        return super.getFrame();
    }

    private void spawnCoinIfNeeded() {
        if (ChronoUnit.MILLIS.between(getLastGoldCheck(), LocalTime.now()) >= millisecondsForGoldCount) {
            final double currentGold = GameStateHelper.getGold();
            if (currentGold - getLastGold() >= goldDiffToSpawnCoin) {
                spawnCoin();
            }
            lastGold = currentGold;
            lastGoldCheck = LocalTime.now();
        }
    }

    public void spawnCoin() {
        final int i = random.nextInt(4);
        switch (i) {
            case 0 -> addToBack(fallingCoin(FIRST_NUMPAD_COLUMN));
            case 1 -> addToBack(fallingCoin(SECOND_NUMPAD_COLUMN));
            case 2 -> addToBack(fallingCoin(THIRD_NUMPAD_COLUMN));
            case 3 -> addToBack(fallingCoin(FOURTH_NUMPAD_COLUMN));
        }
    }

    private AnimatedFrame fallingCoin(List<RzKey> rzKeys) {
        final AnimatedFrame animatedFrames = new AnimatedFrame();
        rzKeys.forEach(rzKey -> animatedFrames.withAnimationFrame(new Frame(rzKey, Color.YELLOW)));
        return animatedFrames;
    }

    public LocalTime getLastGoldCheck() {
        if (lastGoldCheck == null) {
            lastGoldCheck = LocalTime.now();
        }
        return lastGoldCheck;
    }

    public double getLastGold() {
        if (lastGold == null) {
            lastGold = GameStateHelper.getGold();
        }
        return lastGold;
    }
}
