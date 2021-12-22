package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.state.RunningState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class CheckRiotApiTaskTest {

    @Test
    void shouldFailWhenRiotApiNotUp() {
        //given
        RunningState.getRiotApi().setValue(true);

        //when
        new CheckRiotApiTask().run();

        //then
        assertFalse(RunningState.getRiotApi().getValue());
    }
}