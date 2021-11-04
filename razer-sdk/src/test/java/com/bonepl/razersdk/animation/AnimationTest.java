package com.bonepl.razersdk.animation;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.StaticColor;
import com.bonepl.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.NoSuchElementException;

import static com.bonepl.razersdk.sdk.RzKey.RZKEY_ENTER;
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
        animation.addToBack(new SimpleFrame(StaticColor.YELLOW));

        //when
        animation.addToFront(new SimpleFrame(StaticColor.RED));

        //then
        assertTrue(animation.hasFrame());
        final Map<RzKey, Color> actualKeysToColors = animation.getFrame().getKeysToColors();
        assertTrue(actualKeysToColors.values().stream().allMatch(color -> color == StaticColor.RED));
        assertThrows(NoSuchElementException.class, animation::getFrame);
    }

    @Test
    void testAddingFrameToBack() {
        //given
        final Animation animation = new Animation();
        animation.addToFront(new SimpleFrame(StaticColor.RED));

        //when
        animation.addToBack(new SimpleFrame(StaticColor.YELLOW));

        //then
        assertTrue(animation.hasFrame());
        final Map<RzKey, Color> actualKeysToColors = animation.getFrame().getKeysToColors();
        assertTrue(actualKeysToColors.values().stream().allMatch(color -> color == StaticColor.RED));
        assertThrows(NoSuchElementException.class, animation::getFrame);
    }

    @Test
    void testAnimation() {
        //given
        final Animation animation = new Animation();

        final LayeredFrame layeredFrame = new LayeredFrame();
        layeredFrame.addFrame(new SimpleFrame(StaticColor.GREEN));
        animation.addToBack(layeredFrame);

        final AnimatedFrame animatedFrame = new AnimatedFrame();
        animatedFrame.addAnimationFrame(new SimpleFrame(RZKEY_ENTER, StaticColor.RED));
        animatedFrame.addAnimationFrame(new SimpleFrame(RZKEY_ENTER, StaticColor.BLUE));
        animation.addToFront(animatedFrame);

        //when 1st frame
        final Map<RzKey, Color> actualKeysToColors = animation.getFrame().getKeysToColors();

        //then
        assertTrue(animation.hasFrame());
        assertEquals(actualKeysToColors.get(RZKEY_ENTER), StaticColor.RED);
        assertTrue(actualKeysToColors.entrySet().stream()
                .filter(entry -> entry.getKey() != RZKEY_ENTER)
                .map(Map.Entry::getValue).allMatch(color -> color == StaticColor.GREEN));
        assertEquals(RzKey.values().length, actualKeysToColors.size());

        //when 2nd frame
        final Map<RzKey, Color> actualKeysToColors2 = animation.getFrame().getKeysToColors();

        //then
        assertFalse(animation.hasFrame());
        assertEquals(actualKeysToColors2.get(RZKEY_ENTER), StaticColor.BLUE);
        assertEquals(1, actualKeysToColors2.size());
        assertThrows(NoSuchElementException.class, animation::getFrame);
    }
}