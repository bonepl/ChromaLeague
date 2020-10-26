package com.bonepl.razersdk.animation;

import com.bonepl.razersdk.sdk.RzKey2;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FrameTest {
    @Test
    void testCustomEffectCreation() {
        //given
        final EnumMap<RzKey2, Color> inputMap = new EnumMap<>(RzKey2.class);
        inputMap.put(RzKey2.RZKEY_ESC, Color.BLUE);
        inputMap.put(RzKey2.RZKEY_ENTER, Color.WHITE);

        //when
        final Frame frame = new Frame(inputMap);
        final int[] actualColors = frame.toCustomEffect().colors;
        final Map<RzKey2, Color> actualMap = frame.getKeysToColors();

        //then
//        assertEquals(actualColors[RzKey2.RZKEY_ESC.getCustomPosition()], Color.BLUE.getSDKColorRef());
//        assertEquals(actualColors[RzKey2.RZKEY_ENTER.getCustomPosition()], Color.WHITE.getSDKColorRef());
        assertEquals(inputMap, actualMap);
    }
}