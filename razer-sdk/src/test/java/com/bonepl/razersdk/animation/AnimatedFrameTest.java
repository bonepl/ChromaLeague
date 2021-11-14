package com.bonepl.razersdk.animation;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.StaticColor;
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
        animatedFrame.addAnimationFrame(0, new SimpleFrame(StaticColor.RED));
        animatedFrame.addAnimationFrame(-2, new SimpleFrame(StaticColor.RED));

        //then
        assertFalse(animatedFrame.hasFrame());
        assertThrows(NoSuchElementException.class, animatedFrame::getFrame);
    }

    @Test
    void testHasFrames() {
        //given
        final AnimatedFrame animatedFrame = new AnimatedFrame();

        //when
        animatedFrame.addAnimationFrame(new SimpleFrame(StaticColor.RED));
        animatedFrame.addAnimationFrame(2, new SimpleFrame(StaticColor.BLUE));

        //then
        assertHasFrameAndMatches(animatedFrame, StaticColor.RED);
        assertHasFrameAndMatches(animatedFrame, StaticColor.BLUE);
        assertHasFrameAndMatches(animatedFrame, StaticColor.BLUE);
        assertFalse(animatedFrame.hasFrame());
        assertThrows(NoSuchElementException.class, animatedFrame::getFrame);
    }

    @Test
    void testSingleFrameAdd() {
        //given
        final AnimatedFrame animatedFrame = new AnimatedFrame();

        //when
        animatedFrame.addAnimationFrame(new SimpleFrame(StaticColor.BROWN));

        //then
        assertHasFrameAndMatches(animatedFrame, StaticColor.BROWN);
        assertFalse(animatedFrame.hasFrame());
        assertThrows(NoSuchElementException.class, animatedFrame::getFrame);
    }

    @Test
    void testMultipleFrameAdd() {
        //given
        final AnimatedFrame animatedFrame = new AnimatedFrame();

        //when
        animatedFrame.addAnimationFrame(2, new SimpleFrame(StaticColor.ORANGE));

        //then
        assertHasFrameAndMatches(animatedFrame, StaticColor.ORANGE);
        assertHasFrameAndMatches(animatedFrame, StaticColor.ORANGE);
        assertFalse(animatedFrame.hasFrame());
        assertThrows(NoSuchElementException.class, animatedFrame::getFrame);
    }

    @Test
    void testAnimationStop() {
        //given
        final AnimatedFrame animatedFrame = new AnimatedFrame();
        animatedFrame.addAnimationFrame(5, new SimpleFrame(StaticColor.PURPLE));
        assertHasFrameAndMatches(animatedFrame, StaticColor.PURPLE);
        assertHasFrameAndMatches(animatedFrame, StaticColor.PURPLE);

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