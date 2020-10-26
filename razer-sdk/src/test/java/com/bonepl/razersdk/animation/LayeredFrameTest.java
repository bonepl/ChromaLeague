package com.bonepl.razersdk.animation;

import com.bonepl.razersdk.sdk.RzKey2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LayeredFrameTest {

    @Test
    void testEmptyFrameCreation() {
        //given
        final LayeredFrame layeredFrame = new LayeredFrame();

        //when
        final Frame actualFrame = layeredFrame.getFrame();

        //then
        assertTrue(actualFrame.getKeysToColors().isEmpty());
        assertFalse(layeredFrame.hasFrame());
        assertThrows(NoSuchElementException.class, layeredFrame::getFrame);
    }

    @Test
    void testStartWithFrame() {
        //when
        final LayeredFrame layeredFrame = new LayeredFrame();

        //then
        assertTrue(layeredFrame.hasFrame());
    }

    @Test
    void testLayerCreation() {
        //given
        final SimpleFrame layerOne = new SimpleFrame(Arrays.asList(RzKey2.RZKEY_1, RzKey2.RZKEY_2, RzKey2.RZKEY_3), Color.BLUE);
        final SimpleFrame layerTwo = new SimpleFrame(RzKey2.RZKEY_2, Color.RED);
        final SimpleFrame transparentLayer = new SimpleFrame(Arrays.asList(RzKey2.RZKEY_1, RzKey2.RZKEY_2, RzKey2.RZKEY_3, RzKey2.RZKEY_4), Color.NONE);

        //when
        final LayeredFrame layeredFrame = new LayeredFrame();
        layeredFrame.addFrame(layerOne);
        layeredFrame.addFrame(layerTwo);
        layeredFrame.addFrame(transparentLayer);
        final Map<RzKey2, Color> actualColorMap = layeredFrame.getFrame().getKeysToColors();

        //then
        assertEquals(actualColorMap.get(RzKey2.RZKEY_1), Color.BLUE);
        assertEquals(actualColorMap.get(RzKey2.RZKEY_3), Color.BLUE);
        assertEquals(actualColorMap.get(RzKey2.RZKEY_2), Color.RED);
        assertEquals(3, actualColorMap.size());
        assertFalse(layeredFrame.hasFrame());
        assertThrows(NoSuchElementException.class, layeredFrame::getFrame);
    }
}