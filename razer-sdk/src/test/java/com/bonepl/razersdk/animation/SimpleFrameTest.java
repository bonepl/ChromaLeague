package com.bonepl.razersdk.animation;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.color.StaticColor;
import com.bonepl.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SimpleFrameTest {

    @Test
    void testEmptyFrameCreation() {
        //given
        final SimpleFrame simpleFrame = new SimpleFrame();

        //when
        final Frame actualFrame = simpleFrame.getFrame();

        //then
        assertTrue(actualFrame.getKeysToColors().isEmpty());
        assertFalse(simpleFrame.hasFrame());
        assertThrows(NoSuchElementException.class, simpleFrame::getFrame);
    }

    @Test
    void testStartWithFrame() {
        //when
        final SimpleFrame simpleFrame = new SimpleFrame();

        //then
        assertTrue(simpleFrame.hasFrame());
    }

    @Test
    void testFullColor() {
        //given
        final SimpleFrame simpleFrame = new SimpleFrame(StaticColor.BLUE);

        //when
        final Map<RzKey, Color> actualKeysToColors = simpleFrame.getFrame().getKeysToColors();

        //then
        assertFalse(simpleFrame.hasFrame());
        assertEquals(RzKey.values().length, actualKeysToColors.size());
        Arrays.stream(RzKey.values()).forEach(rzKey -> assertEquals(StaticColor.BLUE, actualKeysToColors.get(rzKey)));
        assertThrows(NoSuchElementException.class, simpleFrame::getFrame);
    }

    @Test
    void testOneKey() {
        //given
        final SimpleFrame simpleFrame = new SimpleFrame(RzKey.RZKEY_1, StaticColor.RED);

        //when
        final Map<RzKey, Color> actualKeysToColors = simpleFrame.getFrame().getKeysToColors();

        //then
        assertFalse(simpleFrame.hasFrame());
        assertEquals(1, actualKeysToColors.size());
        assertEquals(StaticColor.RED, actualKeysToColors.get(RzKey.RZKEY_1));
        assertThrows(NoSuchElementException.class, simpleFrame::getFrame);
    }

    @Test
    void testMultipleKeys() {
        //given
        final Collection<RzKey> rzKeys = List.of(RzKey.RZKEY_2, RzKey.RZKEY_3);
        final SimpleFrame simpleFrame = new SimpleFrame(rzKeys, StaticColor.YELLOW);

        //when
        final Map<RzKey, Color> actualKeysToColors = simpleFrame.getFrame().getKeysToColors();

        //then
        assertFalse(simpleFrame.hasFrame());
        assertEquals(2, actualKeysToColors.size());
        rzKeys.forEach(rzKey -> assertEquals(StaticColor.YELLOW, actualKeysToColors.get(rzKey)));
        assertThrows(NoSuchElementException.class, simpleFrame::getFrame);
    }

    @Test
    void testKeyMap() {
        //given
        final EnumMap<RzKey, Color> expectedKeysToColorMap = new EnumMap<>(RzKey.class);
        expectedKeysToColorMap.put(RzKey.RZKEY_Q, StaticColor.GREEN);
        expectedKeysToColorMap.put(RzKey.RZKEY_W, StaticColor.CYAN);
        expectedKeysToColorMap.put(RzKey.RZKEY_E, StaticColor.CYAN);
        final SimpleFrame simpleFrame = new SimpleFrame(expectedKeysToColorMap);

        //when
        final Map<RzKey, Color> actualKeysToColors = simpleFrame.getFrame().getKeysToColors();

        //then
        assertFalse(simpleFrame.hasFrame());
        assertEquals(3, actualKeysToColors.size());
        assertEquals(StaticColor.GREEN, actualKeysToColors.get(RzKey.RZKEY_Q));
        assertEquals(StaticColor.CYAN, actualKeysToColors.get(RzKey.RZKEY_W));
        assertEquals(StaticColor.CYAN, actualKeysToColors.get(RzKey.RZKEY_E));
        assertThrows(NoSuchElementException.class, simpleFrame::getFrame);
    }
}