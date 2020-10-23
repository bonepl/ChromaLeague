package com.bonepl.razersdk.animation;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class AnimatedFrameTest {
    @Test
    void testEmptyFrameCreation() {
        //given
        final AnimatedFrame animatedFrame = new AnimatedFrame();

        //then
        assertThrows(NoSuchElementException.class, animatedFrame::getFrame);
    }

    @Test
    void testStartWithoutFrame() {
        //when
        final AnimatedFrame animatedFrame = new AnimatedFrame();

        //then
        assertFalse(animatedFrame.hasFrame());
    }

    @Test
    void testWrongAmountOfAnimationFrames() {
        //given
        final AnimatedFrame animatedFrame = new AnimatedFrame();

        //when
        animatedFrame.addAnimationFrame(0, new SimpleFrame(Color.RED));
        animatedFrame.addAnimationFrame(-2, new SimpleFrame(Color.RED));

        //then
        assertFalse(animatedFrame.hasFrame());
        assertThrows(NoSuchElementException.class, animatedFrame::getFrame);
    }

    @Test
    void testHasFrames() {
        //given
        final AnimatedFrame animatedFrame = new AnimatedFrame();

        //when
        animatedFrame.addAnimationFrame(new SimpleFrame(Color.RED));
        animatedFrame.addAnimationFrame(2, new SimpleFrame(Color.BLUE));

        //then
        assertHasFrameAndMatches(animatedFrame, Color.RED);
        assertHasFrameAndMatches(animatedFrame, Color.BLUE);
        assertHasFrameAndMatches(animatedFrame, Color.BLUE);
        assertFalse(animatedFrame.hasFrame());
        assertThrows(NoSuchElementException.class, animatedFrame::getFrame);
    }

    @Test
    void testSingleFrameAdd() {
        //given
        final AnimatedFrame animatedFrame = new AnimatedFrame();

        //when
        animatedFrame.addAnimationFrame(new SimpleFrame(Color.BROWN));

        //then
        assertHasFrameAndMatches(animatedFrame, Color.BROWN);
        assertFalse(animatedFrame.hasFrame());
        assertThrows(NoSuchElementException.class, animatedFrame::getFrame);
    }

    @Test
    void testMultipleFrameAdd() {
        //given
        final AnimatedFrame animatedFrame = new AnimatedFrame();

        //when
        animatedFrame.addAnimationFrame(2, new SimpleFrame(Color.ORANGE));

        //then
        assertHasFrameAndMatches(animatedFrame, Color.ORANGE);
        assertHasFrameAndMatches(animatedFrame, Color.ORANGE);
        assertFalse(animatedFrame.hasFrame());
        assertThrows(NoSuchElementException.class, animatedFrame::getFrame);
    }

    @Test
    void testAnimationStop() {
        //given
        final AnimatedFrame animatedFrame = new AnimatedFrame();
        animatedFrame.addAnimationFrame(5, new SimpleFrame(Color.PURPLE));
        assertHasFrameAndMatches(animatedFrame, Color.PURPLE);
        assertHasFrameAndMatches(animatedFrame, Color.PURPLE);

        //when
        animatedFrame.clearFrames();

        //then
        assertFalse(animatedFrame.hasFrame());
        assertThrows(NoSuchElementException.class, animatedFrame::getFrame);
    }

    private static void assertHasFrameAndMatches(AnimatedFrame animatedFrame, Color expectedColor) {
        assertTrue(animatedFrame.hasFrame());
        assertTrue(animatedFrame.getFrame().getKeysToColors().values()
                .stream().allMatch(color -> color == expectedColor));
    }
}