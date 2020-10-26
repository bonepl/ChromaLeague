package com.bonepl.razersdk.animation;

import com.bonepl.razersdk.sdk.RzKey2;
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
        final Map<RzKey2, Color> actualKeysToColors = simpleFrame.getFrame().getKeysToColors();

        //then
        assertFalse(simpleFrame.hasFrame());
        assertEquals(RzKey2.values().length, actualKeysToColors.size());
        Arrays.stream(RzKey2.values()).forEach(rzKey -> assertEquals(Color.BLUE, actualKeysToColors.get(rzKey)));
        assertThrows(NoSuchElementException.class, simpleFrame::getFrame);
    }

    @Test
    void testOneKey() {
        //given
        final SimpleFrame simpleFrame = new SimpleFrame(RzKey2.RZKEY_1, Color.RED);

        //when
        final Map<RzKey2, Color> actualKeysToColors = simpleFrame.getFrame().getKeysToColors();

        //then
        assertFalse(simpleFrame.hasFrame());
        assertEquals(1, actualKeysToColors.size());
        assertEquals(Color.RED, actualKeysToColors.get(RzKey2.RZKEY_1));
        assertThrows(NoSuchElementException.class, simpleFrame::getFrame);
    }

    @Test
    void testMultipleKeys() {
        //given
        final Collection<RzKey2> rzKeys = Arrays.asList(RzKey2.RZKEY_2, RzKey2.RZKEY_3);
        final SimpleFrame simpleFrame = new SimpleFrame(rzKeys, Color.YELLOW);

        //when
        final Map<RzKey2, Color> actualKeysToColors = simpleFrame.getFrame().getKeysToColors();

        //then
        assertFalse(simpleFrame.hasFrame());
        assertEquals(2, actualKeysToColors.size());
        rzKeys.forEach(rzKey -> assertEquals(Color.YELLOW, actualKeysToColors.get(rzKey)));
        assertThrows(NoSuchElementException.class, simpleFrame::getFrame);
    }

    @Test
    void testKeyMap() {
        //given
        final EnumMap<RzKey2, Color> expectedKeysToColorMap = new EnumMap<>(RzKey2.class);
        expectedKeysToColorMap.put(RzKey2.RZKEY_Q, Color.GREEN);
        expectedKeysToColorMap.put(RzKey2.RZKEY_W, Color.CYAN);
        expectedKeysToColorMap.put(RzKey2.RZKEY_E, Color.CYAN);
        final SimpleFrame simpleFrame = new SimpleFrame(expectedKeysToColorMap);

        //when
        final Map<RzKey2, Color> actualKeysToColors = simpleFrame.getFrame().getKeysToColors();

        //then
        assertFalse(simpleFrame.hasFrame());
        assertEquals(3, actualKeysToColors.size());
        assertEquals(Color.GREEN, actualKeysToColors.get(RzKey2.RZKEY_Q));
        assertEquals(Color.CYAN, actualKeysToColors.get(RzKey2.RZKEY_W));
        assertEquals(Color.CYAN, actualKeysToColors.get(RzKey2.RZKEY_E));
        assertThrows(NoSuchElementException.class, simpleFrame::getFrame);
    }
}