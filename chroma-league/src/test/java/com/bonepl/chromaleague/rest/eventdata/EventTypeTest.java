package com.bonepl.chromaleague.rest.eventdata;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.ResourceLoader;
import com.bonepl.chromaleague.hud.parts.dragons.KilledDragonsBarTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EventTypeTest {
    private GameStateMocks gameStateMocks;

    @BeforeEach
    void setUp() {
        gameStateMocks = new GameStateMocks();
    }

    @Test
    void testActivePLayerKill() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);
        final Event event = ResourceLoader.eventFromJson("activePlayerKill.json");

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ACTIVE_PLAYER_KILL, eventType);
    }

    @Test
    void testActivePLayerAssist() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);
        final Event event = ResourceLoader.eventFromJson("activePlayerAssist.json");

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ACTIVE_PLAYER_ASSIST, eventType);
    }

    @Test
    void testAllyOceanDragonAnimation() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);
        final Event event = KilledDragonsBarTest.getEventForDragonKill(DragonType.OCEAN);

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ALLY_OCEAN_DRAGON_KILL, eventType);
    }

    @Test
    void testAllyInfernalDragonAnimation() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);
        final Event event = KilledDragonsBarTest.getEventForDragonKill(DragonType.INFERNAL);

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ALLY_INFERNAL_DRAGON_KILL, eventType);
    }

    @Test
    void testAllyCloudDragonAnimation() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);
        final Event event = KilledDragonsBarTest.getEventForDragonKill(DragonType.CLOUD);

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ALLY_CLOUD_DRAGON_KILL, eventType);
    }

    @Test
    void testAllyMountainDragonAnimation() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);
        final Event event = KilledDragonsBarTest.getEventForDragonKill(DragonType.MOUNTAIN);

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ALLY_MOUNTAIN_DRAGON_KILL, eventType);
    }

    @Test
    void testAllyElderDragonAnimation() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);
        final Event event = KilledDragonsBarTest.getEventForDragonKill(DragonType.ELDER);

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ALLY_ELDER_DRAGON_KILL, eventType);
    }

    @Test
    void testEnemyOceanDragonAnimation() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(false);
        final Event event = KilledDragonsBarTest.getEventForDragonKill(DragonType.OCEAN);

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ENEMY_OCEAN_DRAGON_KILL, eventType);
    }

    @Test
    void testEnemyInfernalDragonAnimation() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(false);
        final Event event = KilledDragonsBarTest.getEventForDragonKill(DragonType.INFERNAL);

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ENEMY_INFERNAL_DRAGON_KILL, eventType);
    }

    @Test
    void testEnemyCloudDragonAnimation() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(false);
        final Event event = KilledDragonsBarTest.getEventForDragonKill(DragonType.CLOUD);

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ENEMY_CLOUD_DRAGON_KILL, eventType);
    }

    @Test
    void testEnemyMountainDragonAnimation() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(false);
        final Event event = KilledDragonsBarTest.getEventForDragonKill(DragonType.MOUNTAIN);

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ENEMY_MOUNTAIN_DRAGON_KILL, eventType);
    }

    @Test
    void testEnemyElderDragonAnimation() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(false);
        final Event event = KilledDragonsBarTest.getEventForDragonKill(DragonType.ELDER);

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ENEMY_ELDER_DRAGON_KILL, eventType);
    }

    @Test
    void testAllyHeraldAnimation() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);
        final Event event = ResourceLoader.eventFromJson("allyHeraldKill.json");

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ALLY_HERALD_KILL, eventType);
    }

    @Test
    void testEnemyHeraldAnimation() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(false);
        final Event event = ResourceLoader.eventFromJson("allyHeraldKill.json");

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ENEMY_HERALD_KILL, eventType);
    }

    @Test
    void testAllyBaronAnimation() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);
        final Event event = ResourceLoader.eventFromJson("allyBaronKill.json");

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ALLY_BARON_KILL, eventType);
    }

    @Test
    void testEnemyBaronAnimation() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(false);
        final Event event = ResourceLoader.eventFromJson("allyBaronKill.json");

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ENEMY_BARON_KILL, eventType);
    }
}