package net.booone.chromaleague.hud.parts.dragons;

import net.booone.chromaleague.GameStateMocks;
import net.booone.chromaleague.hud.AnimationTester;
import net.booone.chromaleague.rest.eventdata.DragonType;
import net.booone.chromaleague.rest.http.LeagueHttpClientMock;
import net.booone.chromaleague.tasks.EventDataProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DragonBarTest {

    @BeforeEach
    void setUp() {
        GameStateMocks gameStateMocks = new GameStateMocks();
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);
        when(gameStateMocks.playerList().getActivePlayer().isDead()).thenReturn(false);
        new LeagueHttpClientMock().mockGameStatsResponse("json/gamestats.json");
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    void testFullDragonBar() {
        Queue<DragonType> testDrakesOrder = new LinkedList<>(List.of(DragonType.INFERNAL,
                DragonType.CLOUD, DragonType.OCEAN, DragonType.OCEAN));
        final EventDataProcessor eventDataProcessor = new EventDataProcessor();

        final DragonBar dragonBar = new DragonBar();
        new AnimationTester()
                .withAfterIterationAction(i -> {
                    if (i % 5 == 0 && !testDrakesOrder.isEmpty()) {
                        eventDataProcessor.processEventForEventData(KilledDragonsBarTest.getEventForDragonKill(testDrakesOrder.remove()));
                    }
                    if (i == 80) {
                        eventDataProcessor.processEventForEventData(KilledDragonsBarTest.getEventForDragonKill(DragonType.ELDER));
                    }
                })
                .testAnimation(dragonBar, 150);
    }
}