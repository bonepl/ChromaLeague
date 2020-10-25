package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.hud.parts.DragonSoulBar;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

class DragonSoulBarTest {
    private static RazerSDKClient razerSDKClient;

    @BeforeAll
    static void beforeAll() {
        razerSDKClient = new RazerSDKClient();
    }

    @AfterAll
    static void afterAll() {
        razerSDKClient.close();
    }

    @Test
    void playCloudDragonSoulAnimation() throws InterruptedException {
        testDragonSoulAnimation(DragonType.CLOUD);
    }

    @Test
    void playInfernalDragonSoulAnimation() throws InterruptedException {
        testDragonSoulAnimation(DragonType.INFERNAL);
    }

    @Test
    void playMountainDragonSoulAnimation() throws InterruptedException {
        testDragonSoulAnimation(DragonType.MOUNTAIN);
    }

    @Test
    void playOceanDragonSoulAnimation() throws InterruptedException {
        testDragonSoulAnimation(DragonType.OCEAN);
    }

    private void testDragonSoulAnimation(DragonType dragonType) throws InterruptedException {
        DragonSoulBar dragonSoulBar = new DragonSoulBar();
        switchToDragon(dragonType);
        for (int i = 0; i < 80; i++) {
            razerSDKClient.createKeyboardEffect(dragonSoulBar);
            Thread.sleep(50);
        }
    }

    private void switchToDragon(DragonType dragonType) {
        GameStateHelper.getKilledDragons().clear();
        IntStream.range(0, 4).forEach(i -> GameStateHelper.addKilledDragon(dragonType));
    }
}