package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.rest.http.LeagueHttpClientMock;
import com.bonepl.chromaleague.state.RunningState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.bonepl.chromaleague.GameStateMocks.PLAYER_NAME;
import static com.bonepl.chromaleague.rest.http.handlers.PlayerListResponseHandlerTest.verifyPlayerList;

class FetchPlayerListTaskTest {

    @AfterEach
    void tearDown() {
        RunningState.setRunningGame(false);
    }

    @Test
    void testPlayerListParsing() {
        //given
        RunningState.getGameState().setPlayerName(PLAYER_NAME);
        new LeagueHttpClientMock().mockPlayerListResponse("json/playerlist.json");

        //when
        new FetchPlayerListTask().run();

        //then
        verifyPlayerList(RunningState.getGameState().getPlayerList());
    }
}