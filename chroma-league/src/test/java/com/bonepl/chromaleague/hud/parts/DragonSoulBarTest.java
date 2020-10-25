package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.hud.animations.AnimationTester;
import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.chromaleague.state.GameStateHelper;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

class DragonSoulBarTest {

    @Test
    void playCloudDragonSoulAnimation() {
        testDragonSoulAnimation(DragonType.CLOUD);
    }

    @Test
    void playInfernalDragonSoulAnimation() {
        testDragonSoulAnimation(DragonType.INFERNAL);
    }

    @Test
    void playMountainDragonSoulAnimation() {
        testDragonSoulAnimation(DragonType.MOUNTAIN);
    }

    @Test
    void playOceanDragonSoulAnimation() {
        testDragonSoulAnimation(DragonType.OCEAN);
    }

    private void testDragonSoulAnimation(DragonType dragonType) {
        switchToDragon(dragonType);
        new AnimationTester().testAnimation(new DragonSoulBar(), 80);
    }

    private void switchToDragon(DragonType dragonType) {
        GameStateHelper.getKilledDragons().clear();
        IntStream.range(0, 4).forEach(i -> GameStateHelper.addKilledDragon(dragonType));
    }
}