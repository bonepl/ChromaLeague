package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.GameState;
import com.bonepl.chromaleague.hud.animations.EventAnimation;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.rest.eventdata.Event;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import com.bonepl.chromaleague.tasks.EventAnimationProcessorTask;
import com.bonepl.razersdk.RazerSDKClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EventAnimationProcessorTaskTest {
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
        ActivePlayer activePlayer = mock(ActivePlayer.class);
        when(activePlayer.getSummonerName()).thenReturn(player);
        GameState.setActivePlayer(activePlayer);
        final Event mock = mock(Event.class);
        when(mock.getEventName()).thenReturn("ChampionKill");
        when(mock.getKillerName()).thenReturn(player);

        //then
        EventAnimationProcessorTask.addEvents(List.of(mock));
        runEventAnimation();
    }

    @Test
    void testAllyOceanDragonAnimation() {
        //given
        EventAnimationProcessorTask.addEvents(Collections.singletonList(mockDragonEvent("Water")));
        mockPlayerList(true);

        //when
        new EventAnimationProcessorTask().run();

        //then
        runEventAnimation();
    }

    @Test
    void testAllyInfernalDragonAnimation() {
        //given
        EventAnimationProcessorTask.addEvents(Collections.singletonList(mockDragonEvent("Fire")));
        mockPlayerList(true);

        //when
        new EventAnimationProcessorTask().run();

        //then
        runEventAnimation();
    }

    @Test
    void testAllyCloudDragonAnimation() {
        //given
        EventAnimationProcessorTask.addEvents(Collections.singletonList(mockDragonEvent("Air")));
        mockPlayerList(true);

        //when
        new EventAnimationProcessorTask().run();

        //then
        runEventAnimation();
    }

    @Test
    void testAllyMountainDragonAnimation() {
        //given
        EventAnimationProcessorTask.addEvents(Collections.singletonList(mockDragonEvent("Earth")));
        mockPlayerList(true);

        //when
        new EventAnimationProcessorTask().run();

        //then
        runEventAnimation();
    }

    @Test
    void testAllyElderDragonAnimation() {
        //given
        EventAnimationProcessorTask.addEvents(Collections.singletonList(mockDragonEvent("Elder")));
        mockPlayerList(true);

        //when
        new EventAnimationProcessorTask().run();

        //then
    }

    @Test
    void testAllyHeraldAnimation() {
        //given
        EventAnimationProcessorTask.addEvents(Collections.singletonList(mockHeraldEvent()));
        mockPlayerList(true);

        //when
        new EventAnimationProcessorTask().run();

        //then
        runEventAnimation();
    }

    @Test
    void testAllyBaronAnimation() {
        //given
        EventAnimationProcessorTask.addEvents(Collections.singletonList(mockBaronEvent()));
        mockPlayerList(true);

        //when
        new EventAnimationProcessorTask().run();

        //then
        runEventAnimation();
    }

    @Test
    void testEnemyOceanDragonAnimation() {
        //given
        EventAnimationProcessorTask.addEvents(Collections.singletonList(mockDragonEvent("Water")));
        mockPlayerList(false);

        //when
        new EventAnimationProcessorTask().run();

        //then
        runEventAnimation();
    }

    @Test
    void testEnemyInfernalDragonAnimation() {
        //given
        EventAnimationProcessorTask.addEvents(Collections.singletonList(mockDragonEvent("Fire")));
        mockPlayerList(false);

        //when
        new EventAnimationProcessorTask().run();

        //then
        runEventAnimation();
    }

    @Test
    void testEnemyCloudDragonAnimation() {
        //given
        EventAnimationProcessorTask.addEvents(Collections.singletonList(mockDragonEvent("Air")));
        mockPlayerList(false);

        //when
        new EventAnimationProcessorTask().run();

        //then
        runEventAnimation();
    }

    @Test
    void testEnemyMountainDragonAnimation() {
        //given
        EventAnimationProcessorTask.addEvents(Collections.singletonList(mockDragonEvent("Earth")));
        mockPlayerList(false);

        //when
        new EventAnimationProcessorTask().run();

        //then
        runEventAnimation();
    }

    @Test
    void testEnemyElderDragonAnimation() {
        //given
        EventAnimationProcessorTask.addEvents(Collections.singletonList(mockDragonEvent("Elder")));
        mockPlayerList(false);

        //when
        new EventAnimationProcessorTask().run();

        //then
        runEventAnimation();
    }

    @Test
    void testEnemyHeraldAnimation() {
        //given
        EventAnimationProcessorTask.addEvents(Collections.singletonList(mockHeraldEvent()));
        mockPlayerList(false);

        //when
        new EventAnimationProcessorTask().run();

        //then
        runEventAnimation();
    }

    @Test
    void testEnemyBaronAnimation() {
        //given
        EventAnimationProcessorTask.addEvents(Collections.singletonList(mockBaronEvent()));
        mockPlayerList(false);

        //when
        new EventAnimationProcessorTask().run();

        //then
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