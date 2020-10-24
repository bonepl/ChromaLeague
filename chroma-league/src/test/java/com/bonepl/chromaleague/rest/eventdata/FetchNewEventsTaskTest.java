package com.bonepl.chromaleague.rest.eventdata;

import com.bonepl.chromaleague.tasks.EventAnimationProcessorTask;
import com.bonepl.chromaleague.tasks.EventDataProcessorTask;
import com.bonepl.chromaleague.state.GameState;
import com.bonepl.chromaleague.rest.LeagueHttpClientMocker;
import com.bonepl.chromaleague.rest.activeplayer.ActivePlayer;
import com.bonepl.chromaleague.rest.playerlist.PlayerList;
import com.bonepl.chromaleague.tasks.FetchNewEventsTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FetchNewEventsTaskTest {

    @BeforeEach
    void setUp() {
        final String player = "BooonE";
        ActivePlayer activePlayer = mock(ActivePlayer.class);
        when(activePlayer.getSummonerName()).thenReturn(player);
        GameState.setActivePlayer(activePlayer);
        PlayerList playerList = mock(PlayerList.class);
        GameState.setPlayerList(playerList);

        EventAnimationProcessorTask.getUnprocessedEvents().clear();
        EventDataProcessorTask.getUnprocessedEvents().clear();
    }

    @Test
    void testEventParsing() {
        //given
        LeagueHttpClientMocker.mockReturnedResponseWithResource("json/eventdata.json");
        FetchNewEventsTask.resetProcessedEventCounter();

        //when
        new FetchNewEventsTask().run();

        //then
        List<Event> events = new ArrayList<>(30);
        events.addAll(EventDataProcessorTask.getUnprocessedEvents());
        assertEquals(27, events.size());
        final Event event = events.get(0);
        assertEquals(0, event.getEventID());
        assertEquals("GameStart", event.getEventName());
        assertEquals(0.0563616007566452, event.getEventTime());
    }

    @Test
    void testFirstEventParsing() {
        //given
        LeagueHttpClientMocker.mockReturnedResponseWithResource("json/gamestartevent.json");

        //when
        new FetchNewEventsTask().run();

        //then
        List<Event> events = new ArrayList<>(1);
        events.addAll(EventDataProcessorTask.getUnprocessedEvents());
        assertEquals(1, events.size());
        final Event event = events.get(0);
        assertEquals(0, event.getEventID());
        assertEquals("GameStart", event.getEventName());
        assertEquals(0.0563616007566452, event.getEventTime());
    }

    @Test
    void testEventsSkipAfterReconnect() {
        //given
        LeagueHttpClientMocker.mockReturnedResponseWithResource("json/eventdata.json");
        FetchNewEventsTask.resetProcessedEventCounter();

        //when
        new FetchNewEventsTask().run();

        assertTrue(EventAnimationProcessorTask.getUnprocessedEvents().isEmpty());
    }
}