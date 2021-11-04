package com.bonepl.razersdk.animation;

import com.bonepl.razersdk.color.Color;
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
        final SimpleFrame simpleFrame = new SimpleFrame(Color.BLUE);

        //when
        final Map<RzKey, Color> actualKeysToColors = simpleFrame.getFrame().getKeysToColors();

        //then
        assertFalse(simpleFrame.hasFrame());
        assertEquals(RzKey.values().length, actualKeysToColors.size());
        Arrays.stream(RzKey.values()).forEach(rzKey -> assertEquals(Color.BLUE, actualKeysToColors.get(rzKey)));
        assertThrows(NoSuchElementException.class, simpleFrame::getFrame);
    }

    @Test
    void testOneKey() {
        //given
        final SimpleFrame simpleFrame = new SimpleFrame(RzKey.RZKEY_1, Color.RED);

        //when
        final Map<RzKey, Color> actualKeysToColors = simpleFrame.getFrame().getKeysToColors();

        //then
        assertFalse(simpleFrame.hasFrame());
        assertEquals(1, actualKeysToColors.size());
        assertEquals(Color.RED, actualKeysToColors.get(RzKey.RZKEY_1));
        assertThrows(NoSuchElementException.class, simpleFrame::getFrame);
    }

    @Test
    void testMultipleKeys() {
        //given
        final Collection<RzKey> rzKeys = List.of(RzKey.RZKEY_2, RzKey.RZKEY_3);
        final SimpleFrame simpleFrame = new SimpleFrame(rzKeys, Color.YELLOW);

        //when
        final Map<RzKey, Color> actualKeysToColors = simpleFrame.getFrame().getKeysToColors();

        //then
        assertFalse(simpleFrame.hasFrame());
        assertEquals(2, actualKeysToColors.size());
        rzKeys.forEach(rzKey -> assertEquals(Color.YELLOW, actualKeysToColors.get(rzKey)));
        assertThrows(NoSuchElementException.class, simpleFrame::getFrame);
    }

    @Test
    void testKeyMap() {
        //given
        final EnumMap<RzKey, Color> expectedKeysToColorMap = new EnumMap<>(RzKey.class);
        expectedKeysToColorMap.put(RzKey.RZKEY_Q, Color.GREEN);
        expectedKeysToColorMap.put(RzKey.RZKEY_W, Color.CYAN);
        expectedKeysToColorMap.put(RzKey.RZKEY_E, Color.CYAN);
        final SimpleFrame simpleFrame = new SimpleFrame(expectedKeysToColorMap);

        //when
        final Map<RzKey, Color> actualKeysToColors = simpleFrame.getFrame().getKeysToColors();

        //then
        assertFalse(simpleFrame.hasFrame());
        assertEquals(3, actualKeysToColors.size());
        assertEquals(Color.GREEN, actualKeysToColors.get(RzKey.RZKEY_Q));
        assertEquals(Color.CYAN, actualKeysToColors.get(RzKey.RZKEY_W));
        assertEquals(Color.CYAN, actualKeysToColors.get(RzKey.RZKEY_E));
        assertThrows(NoSuchElementException.class, simpleFrame::getFrame);
    }
}