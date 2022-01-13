package net.booone.chromaleague.tasks;

import net.booone.chromaleague.state.RunningState;
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