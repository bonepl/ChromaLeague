package com.bonepl.chromaleague.hud.parts.dragons;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.hud.AnimationTester;
import com.bonepl.chromaleague.rest.LeagueHttpClientMock;
import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.chromaleague.rest.playerlist.Player;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import com.bonepl.chromaleague.tasks.EventDataProcessor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DragonBarTest {
    @BeforeAll
    static void beforeAll() {
        GameStateMocks.mockActivePlayer("BooonE");
        final Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);
        final PlayerList mockedPlayerList = GameStateMocks.mockPlayerList();
        when(mockedPlayerList.isAlly(any())).thenReturn(true);
        when(mockedPlayerList.getActivePlayer()).thenReturn(player);
        new LeagueHttpClientMock().mockGameStatsResponse("json/gamestats.json");
    }

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