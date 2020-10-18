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
import java.util.List;
import java.util.Random;

import static com.bonepl.chromaleague.hud.PredefinedKeySets.*;

public class GoldAnimation extends Animation {
    private static final Logger logger = LogManager.getLogger();

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
