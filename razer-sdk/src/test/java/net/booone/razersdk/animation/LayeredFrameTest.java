package net.booone.razersdk.animation;

import net.booone.razersdk.color.Color;
import net.booone.razersdk.color.StaticColor;
import net.booone.razersdk.sdk.RzKey;
import org.junit.jupiter.api.Test;

import java.util.List;
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
        final LayeredFrame layeredFrame = createTestLayer();
        final Map<RzKey, Color> actualColorMap = layeredFrame.getFrame().getKeysToColors();

        //then
        assertEquals(actualColorMap.get(RzKey.RZKEY_1), StaticColor.BLUE);
        assertEquals(actualColorMap.get(RzKey.RZKEY_3), StaticColor.BLUE);
        assertEquals(actualColorMap.get(RzKey.RZKEY_2), StaticColor.RED);
        assertEquals(3, actualColorMap.size());
        assertFalse(layeredFrame.hasFrame());
        assertThrows(NoSuchElementException.class, layeredFrame::getFrame);
    }

    private static LayeredFrame createTestLayer() {
        final SimpleFrame layerOne = new SimpleFrame(List.of(RzKey.RZKEY_1, RzKey.RZKEY_2, RzKey.RZKEY_3), StaticColor.BLUE);
        final SimpleFrame layerTwo = new SimpleFrame(RzKey.RZKEY_2, StaticColor.RED);
        final SimpleFrame transparentLayer = new SimpleFrame(List.of(RzKey.RZKEY_1, RzKey.RZKEY_2, RzKey.RZKEY_3, RzKey.RZKEY_4), StaticColor.NONE);

        //when
        final LayeredFrame layeredFrame = new LayeredFrame();
        layeredFrame.addFrame(layerOne);
        layeredFrame.addFrame(layerTwo);
        layeredFrame.addFrame(transparentLayer);
        return layeredFrame;
    }
}