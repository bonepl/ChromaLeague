package net.booone.chromaleague.hud.parts.dragons;

import net.booone.chromaleague.GameStateMocks;
import net.booone.chromaleague.ResourceLoader;
import net.booone.chromaleague.hud.AnimationTester;
import net.booone.chromaleague.rest.eventdata.DragonType;
import net.booone.chromaleague.rest.eventdata.Event;
import net.booone.chromaleague.tasks.EventDataProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class KilledDragonsBarTest {

    @BeforeEach
    void setUp() {
        when(new GameStateMocks().playerList().isAlly(any())).thenReturn(true);
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

    @Test
    void testChemtechDragons() {
        Queue<DragonType> testDrakesOrder = new LinkedList<>(List.of(DragonType.HEXTECH,
                DragonType.INFERNAL, DragonType.CHEMTECH, DragonType.CHEMTECH));

        testDragonsWithSoul(testDrakesOrder);
    }

    @Test
    void testHextechDragons() {
        Queue<DragonType> testDrakesOrder = new LinkedList<>(List.of(DragonType.CHEMTECH,
                DragonType.INFERNAL, DragonType.HEXTECH, DragonType.HEXTECH));

        testDragonsWithSoul(testDrakesOrder);
    }

    private static void testDragonsWithSoul(Queue<DragonType> testDrakesOrder) {
        final EventDataProcessor eventDataProcessor = new EventDataProcessor();
        KilledDragonsBar killedDragonsBar = new KilledDragonsBar();
        new AnimationTester()
                .withAfterIterationAction(i -> {
                    if (i % 5 == 0 && !testDrakesOrder.isEmpty()) {
                        eventDataProcessor.processEventForEventData(getEventForDragonKill(testDrakesOrder.remove()));
                    }
                })
                .testAnimation(killedDragonsBar, 80);
    }

    public static Event getEventForDragonKill(DragonType dragonType) {
        return ResourceLoader.eventFromJson(switch (dragonType) {
            case CHEMTECH -> "allyChemtechDragonKill.json";
            case CLOUD -> "allyCloudDragonKill.json";
            case ELDER -> "allyElderDragonKill.json";
            case HEXTECH -> "allyHextechDragonKill.json";
            case INFERNAL -> "allyInfernalDragonKill.json";
            case OCEAN -> "allyOceanDragonKill.json";
            case MOUNTAIN -> "allyMountainDragonKill.json";
        });
    }
}