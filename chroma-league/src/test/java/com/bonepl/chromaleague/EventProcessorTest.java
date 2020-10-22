package com.bonepl.chromaleague;

import com.bonepl.chromaleague.hud.animations.EventAnimation;
import com.bonepl.chromaleague.rest.eventdata.model.Event;
import com.bonepl.chromaleague.rest.playerlist.model.PlayerList;
import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EventProcessorTest {
    private static RazerSDKClient razerSDKClient;
    private static final EventAnimation eventAnimation = new EventAnimation();

    @BeforeAll
    static void beforeAll() {
        razerSDKClient = new RazerSDKClient();
    }

    @AfterAll
    static void afterAll() {
        razerSDKClient.close();
    }

    @Test
    void testActivePLayerKill() {
        //given
        final String player = "BooonE";
        GameState.setActivePlayerName(player);
        final Event mock = mock(Event.class);
        when(mock.getEventName()).thenReturn("ChampionKill");
        when(mock.getKillerName()).thenReturn(player);
        GameState.addUnprocessedEvents(List.of(mock));

        //then
        EventProcessor.processEvents();
        runEventAnimation();
    }

    @Test
    void testAllyOceanDragonAnimation() {
        //given
        GameState.addUnprocessedEvents(Collections.singletonList(mockDragonEvent("Water")));
        mockPlayerList(true);

        //then
        EventProcessor.processEvents();
        runEventAnimation();
    }

    private void runEventAnimation() {
        while (eventAnimation.hasFrame()) {
            razerSDKClient.createKeyboardEffect(eventAnimation);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void testAllyInfernalDragonAnimation() {
        //given
        GameState.addUnprocessedEvents(Collections.singletonList(mockDragonEvent("Fire")));
        mockPlayerList(true);

        //then
        EventProcessor.processEvents();
        runEventAnimation();
    }

    @Test
    void testAllyCloudDragonAnimation() {
        //given
        GameState.addUnprocessedEvents(Collections.singletonList(mockDragonEvent("Air")));
        mockPlayerList(true);

        //then
        EventProcessor.processEvents();
        runEventAnimation();
    }

    @Test
    void testAllyMountainDragonAnimation() {
        //given
        GameState.addUnprocessedEvents(Collections.singletonList(mockDragonEvent("Earth")));
        mockPlayerList(true);

        //then
        EventProcessor.processEvents();
        runEventAnimation();
    }

    @Test
    void testAllyElderDragonAnimation() {
        //given
        GameState.addUnprocessedEvents(Collections.singletonList(mockDragonEvent("Elder")));
        mockPlayerList(true);

        //then
        EventProcessor.processEvents();
    }

    @Test
    void testAllyHeraldAnimation() {
        //given
        GameState.addUnprocessedEvents(Collections.singletonList(mockHeraldEvent()));
        mockPlayerList(true);

        //then
        EventProcessor.processEvents();
        runEventAnimation();
    }

    @Test
    void testAllyBaronAnimation() {
        //given
        GameState.addUnprocessedEvents(Collections.singletonList(mockBaronEvent()));
        mockPlayerList(true);

        //then
        EventProcessor.processEvents();
        runEventAnimation();
    }

    @Test
    void testEnemyOceanDragonAnimation() {
        //given
        GameState.addUnprocessedEvents(Collections.singletonList(mockDragonEvent("Water")));
        mockPlayerList(false);

        //then
        EventProcessor.processEvents();
        runEventAnimation();
    }

    @Test
    void testEnemyInfernalDragonAnimation() {
        //given
        GameState.addUnprocessedEvents(Collections.singletonList(mockDragonEvent("Fire")));
        mockPlayerList(false);

        //then
        EventProcessor.processEvents();
        runEventAnimation();
    }

    @Test
    void testEnemyCloudDragonAnimation() {
        //given
        GameState.addUnprocessedEvents(Collections.singletonList(mockDragonEvent("Air")));
        mockPlayerList(false);

        //then
        EventProcessor.processEvents();
        runEventAnimation();
    }

    @Test
    void testEnemyMountainDragonAnimation() {
        //given
        GameState.addUnprocessedEvents(Collections.singletonList(mockDragonEvent("Earth")));
        mockPlayerList(false);

        //then
        EventProcessor.processEvents();
        runEventAnimation();
    }

    @Test
    void testEnemyElderDragonAnimation() {
        //given
        GameState.addUnprocessedEvents(Collections.singletonList(mockDragonEvent("Elder")));
        mockPlayerList(false);

        //then
        EventProcessor.processEvents();
        runEventAnimation();
    }

    @Test
    void testEnemyHeraldAnimation() {
        //given
        GameState.addUnprocessedEvents(Collections.singletonList(mockHeraldEvent()));
        mockPlayerList(false);

        //then
        EventProcessor.processEvents();
        runEventAnimation();
    }

    @Test
    void testEnemyBaronAnimation() {
        //given
        GameState.addUnprocessedEvents(Collections.singletonList(mockBaronEvent()));
        mockPlayerList(false);

        //then
        EventProcessor.processEvents();
        runEventAnimation();
    }

    private Event mockDragonEvent(String dragonType) {
        final Event mock = mock(Event.class);
        when(mock.getEventName()).thenReturn("DragonKill");
        when(mock.getDragonType()).thenReturn(dragonType);
        return mock;
    }

    private Event mockHeraldEvent() {
        final Event mock = mock(Event.class);
        when(mock.getEventName()).thenReturn("HeraldKill");
        return mock;
    }

    private Event mockBaronEvent() {
        final Event mock = mock(Event.class);
        when(mock.getEventName()).thenReturn("BaronKill");
        return mock;
    }

    private void mockPlayerList(boolean isAlly) {
        final PlayerList mock = mock(PlayerList.class);
        when(mock.isAlly(any())).thenReturn(isAlly);
        GameState.setPlayerList(mock);
    }
}