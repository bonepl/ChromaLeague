package com.bonepl.chromaleague.hud.animations;

import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.chromaleague.hud.parts.GoldBar;
import com.bonepl.razersdk.animation.*;
import com.bonepl.razersdk.sdk.RzKey;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import static com.bonepl.chromaleague.hud.PredefinedKeySets.*;

public class GoldAnimation extends Animation {
    private static final Random random = new Random();
    private final double goldDiffToSpawnCoin;
    private final int millisecondsForGoldCount;
    private Double lastGold;
    private LocalTime lastGoldCheck;

    public GoldAnimation() {
        this(10, 1000);
    }

    //FOR TEST ONLY
    public GoldAnimation(double goldDiffToSpawnCoin, int millisecondsForGoldCount) {
        this.goldDiffToSpawnCoin = goldDiffToSpawnCoin;
        this.millisecondsForGoldCount = millisecondsForGoldCount;
    }

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
        rzKeys.forEach(rzKey -> animatedFrames.addAnimationFrame(new SimpleFrame(rzKey, Color.YELLOW)));
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
