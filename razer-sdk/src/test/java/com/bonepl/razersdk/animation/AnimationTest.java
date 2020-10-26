package com.bonepl.razersdk.animation;

import com.bonepl.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class AnimationTest {
    @Test
    void testEmptyFrameCreation() {
        //given
        final Animation animation = new Animation();

        //then
        assertFalse(animation.hasFrame());
        assertThrows(NoSuchElementException.class, animation::getFrame);
    }

    @Test
    void testAddingFrameToFront() {
        //given
        final Animation animation = new Animation();
        animation.addToBack(new SimpleFrame(Color.YELLOW));

        //when
        animation.addToFront(new SimpleFrame(Color.RED));

        //then
        assertTrue(animation.hasFrame());
        final Map<RzKey, Color> actualKeysToColors = animation.getFrame().getKeysToColors();
        assertTrue(actualKeysToColors.values().stream().allMatch(color -> color == Color.RED));
        assertThrows(NoSuchElementException.class, animation::getFrame);
    }

    @Test
    void testAddingFrameToBack() {
        //given
        final Animation animation = new Animation();
        animation.addToFront(new SimpleFrame(Color.RED));

        //when
        animation.addToBack(new SimpleFrame(Color.YELLOW));

        //then
        assertTrue(animation.hasFrame());
        final Map<RzKey, Color> actualKeysToColors = animation.getFrame().getKeysToColors();
        assertTrue(actualKeysToColors.values().stream().allMatch(color -> color == Color.RED));
        assertThrows(NoSuchElementException.class, animation::getFrame);
    }

    @Test
    void testAnimation() {
        //given
        final Animation animation = new Animation();

        final LayeredFrame layeredFrame = new LayeredFrame();
        layeredFrame.addFrame(new SimpleFrame(Color.GREEN));
        animation.addToBack(layeredFrame);

        final AnimatedFrame animatedFrame = new AnimatedFrame();
        animatedFrame.addAnimationFrame(new SimpleFrame(RzKey.RZKEY_ENTER, Color.RED));
        animatedFrame.addAnimationFrame(new SimpleFrame(RzKey.RZKEY_ENTER, Color.BLUE));
        animation.addToFront(animatedFrame);

        //when 1st frame
        final Map<RzKey, Color> actualKeysToColors = animation.getFrame().getKeysToColors();

        //then
        assertTrue(animation.hasFrame());
        assertEquals(actualKeysToColors.get(RzKey.RZKEY_ENTER), Color.RED);
        assertTrue(actualKeysToColors.entrySet().stream()
                .filter(entry -> entry.getKey() != RzKey.RZKEY_ENTER)
                .map(Map.Entry::getValue).allMatch(color -> color == Color.GREEN));
        assertEquals(RzKey.values().length, actualKeysToColors.size());

        //when 2nd frame
        final Map<RzKey, Color> actualKeysToColors2 = animation.getFrame().getKeysToColors();

        //then
        assertFalse(animation.hasFrame());
        assertEquals(actualKeysToColors2.get(RzKey.RZKEY_ENTER), Color.BLUE);
        assertEquals(1, actualKeysToColors2.size());
        assertThrows(NoSuchElementException.class, animation::getFrame);
    }
}