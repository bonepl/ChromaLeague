package com.bonepl.chromaleague.hud.parts.dragons;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.ResourceLoader;
import com.bonepl.chromaleague.hud.animations.AnimationTester;
import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.chromaleague.rest.eventdata.Event;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.chromaleague.tasks.EventDataProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class KilledDragonsBarTest {
    @BeforeEach
    void setUp() {
        GameStateMocks.mockActivePlayer("BooonE");
        final PlayerList mockedPlayerList = GameStateMocks.mockPlayerList();
        when(mockedPlayerList.isAlly(any())).thenReturn(true);
    }

    @AfterEach
    void tearDown() {
        RunningState.setRunningGame(false);
    }

    @Test
    void testOceanDragons() {
        Queue<DragonType> testDrakesOrder = new LinkedList<>(List.of(DragonType.INFERNAL,
                DragonType.CLOUD, DragonType.OCEAN, DragonType.OCEAN));

        testDragonsWithSoul(testDrakesOrder);
    }

    @Test
    void testInfernalDragons() {
        Queue<DragonType> testDrakesOrder = new LinkedList<>(List.of(DragonType.OCEAN,
                DragonType.MOUNTAIN, DragonType.INFERNAL, DragonType.INFERNAL));

        testDragonsWithSoul(testDrakesOrder);
    }

    @Test
    void testCloudDragons() {
        Queue<DragonType> testDrakesOrder = new LinkedList<>(List.of(DragonType.MOUNTAIN,
                DragonType.OCEAN, DragonType.CLOUD, DragonType.CLOUD));

        testDragonsWithSoul(testDrakesOrder);
    }

    @Test
    void testMountainDragons() {
        Queue<DragonType> testDrakesOrder = new LinkedList<>(List.of(DragonType.CLOUD,
                DragonType.INFERNAL, DragonType.MOUNTAIN, DragonType.MOUNTAIN));

        testDragonsWithSoul(testDrakesOrder);
    }

    private static void testDragonsWithSoul(Queue<DragonType> testDrakesOrder) {
        final EventDataProcessor eventDataProcessor = new EventDataProcessor();
        new AnimationTester()
                .withAfterIterationAction(i -> {
                    if (i % 5 == 0 && !testDrakesOrder.isEmpty()) {
                        eventDataProcessor.processEventForEventData(getEventForDragonKill(testDrakesOrder.remove()));
                    }
                })
                .testAnimation(KilledDragonsBar::new, 80);
    }

    public static Event getEventForDragonKill(DragonType dragonType) {
        return ResourceLoader.eventFromJson(switch (dragonType) {
            case CLOUD -> "allyCloudDragonKill.json";
            case OCEAN -> "allyOceanDragonKill.json";
            case INFERNAL -> "allyInfernalDragonKill.json";
            case MOUNTAIN -> "allyMountainDragonKill.json";
            case ELDER -> "allyElderDragonKill.json";
        });
    }
}