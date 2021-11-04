package com.bonepl.razersdk.color;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransitionColorTest {

    @Test
    void testTooFewStepsTransition() {
        assertThrows(IllegalArgumentException.class, () -> new TransitionColor(StaticColor.RED, StaticColor.GREEN, -1));
        assertThrows(IllegalArgumentException.class, () -> new TransitionColor(StaticColor.RED, StaticColor.GREEN, 0));
        assertThrows(IllegalArgumentException.class, () -> new TransitionColor(StaticColor.RED, StaticColor.GREEN, 1));
    }

    @Test
    void testTwoStepTransition() {
        TransitionColor transitionColor = new TransitionColor(StaticColor.RED, StaticColor.GREEN, 2);
        assertEquals(StaticColor.RED, transitionColor.getColor());
        assertFalse(transitionColor.transitionFinished());
        assertEquals(StaticColor.GREEN, transitionColor.getColor());
        assertTrue(transitionColor.transitionFinished());
    }

    @Test
    void testThreeStepTransition() {
        TransitionColor transitionColor = new TransitionColor(StaticColor.RED, StaticColor.GREEN, 3);
        assertEquals(StaticColor.RED, transitionColor.getColor());
        assertFalse(transitionColor.transitionFinished());
        assertEquals(new StaticColor(128,127,0), transitionColor.getColor());
        assertFalse(transitionColor.transitionFinished());
        assertEquals(StaticColor.GREEN, transitionColor.getColor());
        assertTrue(transitionColor.transitionFinished());
    }
}