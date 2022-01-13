package net.booone.chromaleague.rest.eventdata;

import net.booone.chromaleague.GameStateMocks;
import net.booone.chromaleague.ResourceLoader;
import net.booone.chromaleague.hud.parts.dragons.KilledDragonsBarTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EventTypeTest {
    private GameStateMocks gameStateMocks;

    @BeforeEach
    void setUp() {
        gameStateMocks = new GameStateMocks();
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @MethodSource("dragonToExpectedTypeArguments")
    void testDragonsAnimation(DragonType dragonType, EventType expectedEventType, boolean alliedKill) {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(alliedKill);
        final Event event = KilledDragonsBarTest.getEventForDragonKill(dragonType);

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(expectedEventType, eventType);
    }

    static Stream<Arguments> dragonToExpectedTypeArguments() {
        return Stream.of(
                Arguments.of(DragonType.OCEAN, EventType.ALLY_OCEAN_DRAGON_KILL, true),
                Arguments.of(DragonType.MOUNTAIN, EventType.ALLY_MOUNTAIN_DRAGON_KILL, true),
                Arguments.of(DragonType.INFERNAL, EventType.ALLY_INFERNAL_DRAGON_KILL, true),
                Arguments.of(DragonType.CLOUD, EventType.ALLY_CLOUD_DRAGON_KILL, true),
                Arguments.of(DragonType.CHEMTECH, EventType.ALLY_CHEMTECH_DRAGON_KILL, true),
                Arguments.of(DragonType.HEXTECH, EventType.ALLY_HEXTECH_DRAGON_KILL, true),
                Arguments.of(DragonType.ELDER, EventType.ALLY_ELDER_DRAGON_KILL, true),
                Arguments.of(DragonType.OCEAN, EventType.ENEMY_OCEAN_DRAGON_KILL, false),
                Arguments.of(DragonType.MOUNTAIN, EventType.ENEMY_MOUNTAIN_DRAGON_KILL, false),
                Arguments.of(DragonType.INFERNAL, EventType.ENEMY_INFERNAL_DRAGON_KILL, false),
                Arguments.of(DragonType.CLOUD, EventType.ENEMY_CLOUD_DRAGON_KILL, false),
                Arguments.of(DragonType.CHEMTECH, EventType.ENEMY_CHEMTECH_DRAGON_KILL, false),
                Arguments.of(DragonType.HEXTECH, EventType.ENEMY_HEXTECH_DRAGON_KILL, false),
                Arguments.of(DragonType.ELDER, EventType.ENEMY_ELDER_DRAGON_KILL, false)
        );
    }

    @Test
    void testActivePlayerKill() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);
        final Event event = ResourceLoader.eventFromJson("activePlayerKill.json");

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ACTIVE_PLAYER_KILL, eventType);
    }

    @Test
    void testActivePlayerAssist() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);
        final Event event = ResourceLoader.eventFromJson("activePlayerAssist.json");

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ACTIVE_PLAYER_ASSIST, eventType);
    }

    @Test
    void testAllyHeraldKillEvent() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);
        final Event event = ResourceLoader.eventFromJson("allyHeraldKill.json");

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ALLY_HERALD_KILL, eventType);
    }

    @Test
    void testEnemyHeraldKillEvent() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(false);
        final Event event = ResourceLoader.eventFromJson("allyHeraldKill.json");

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ENEMY_HERALD_KILL, eventType);
    }

    @Test
    void testAllyBaronKillEvent() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(true);
        final Event event = ResourceLoader.eventFromJson("allyBaronKill.json");

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ALLY_BARON_KILL, eventType);
    }

    @Test
    void testEnemyBaronKillEvent() {
        //given
        when(gameStateMocks.playerList().isAlly(any())).thenReturn(false);
        final Event event = ResourceLoader.eventFromJson("allyBaronKill.json");

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.ENEMY_BARON_KILL, eventType);
    }

    @Test
    void testGameEndWinEvent() {
        //given
        final Event event = ResourceLoader.eventFromJson("gameEndWin.json");

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.GAME_END_VICTORY, eventType);
    }

    @Test
    void testGameEndLoseEvent() {
        //given
        final Event event = ResourceLoader.eventFromJson("gameEndLose.json");

        //when
        final EventType eventType = EventType.fromEvent(event);

        //then
        assertEquals(EventType.GAME_END_DEFEAT, eventType);
    }
}