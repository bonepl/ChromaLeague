package com.bonepl.razersdk.sdk;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RzKeySelectorTest {

    @Test
    void testEmptySelection() {
        //when
        final List<RzKey> rzKeys = new RzKeySelector().asList();

        //then
        assertTrue(rzKeys.isEmpty());
    }

    @Test
    void testSingleKeySelection() {
        //given
        final RzKey expected = RzKey.RZKEY_TILDE;

        //when
        final List<RzKey> rzKeys = new RzKeySelector()
                .withRowOf(expected).withColumnOf(expected).asList();

        //then
        assertFalse(rzKeys.isEmpty());
        assertEquals(expected, rzKeys.get(0));
    }

    @Test
    void testRangeSelection() {
        //given
        final List<RzKey> expected = List.of(RzKey.RZKEY_INSERT, RzKey.RZKEY_HOME,
                RzKey.RZKEY_PAGEUP, RzKey.RZKEY_DELETE, RzKey.RZKEY_END, RzKey.RZKEY_PAGEDOWN);

        //when
        final List<RzKey> rzKeys = new RzKeySelector()
                .withColumnBetween(RzKey.RZKEY_INSERT, RzKey.RZKEY_PAGEUP)
                .withRowBetween(RzKey.RZKEY_INSERT, RzKey.RZKEY_DELETE)
                .asList();

        //then
        assertFalse(rzKeys.isEmpty());
        assertEquals(6, rzKeys.size());
        assertTrue(rzKeys.containsAll(expected));
    }

    @Test
    void testRectangleSelection() {
        final List<RzKey> expected = List.of(RzKey.RZKEY_NUMPAD7, RzKey.RZKEY_NUMPAD8,
                RzKey.RZKEY_NUMPAD4, RzKey.RZKEY_NUMPAD5,
                RzKey.RZKEY_NUMPAD1, RzKey.RZKEY_NUMPAD2);

        //when
        final List<RzKey> rzKeys = new RzKeySelector()
                .withRectangleBetween(RzKey.RZKEY_NUMPAD7, RzKey.RZKEY_NUMPAD2).asList();

        //then
        //then
        assertFalse(rzKeys.isEmpty());
        assertEquals(6, rzKeys.size());
        assertTrue(rzKeys.containsAll(expected));
    }

    @Test
    void testColumnSort() {
        //when
        final List<RzKey> rzKeys = new RzKeySelector()
                .withRowBetween(RzKey.RZKEY_1, RzKey.RZKEY_Q)
                .withColumnBetween(RzKey.RZKEY_1, RzKey.RZKEY_2)
                .sortedByColumn().asList();

        //then
        assertFalse(rzKeys.isEmpty());
        assertEquals(4, rzKeys.size());
        assertTrue(rzKeys.subList(0, 2).stream().allMatch(rzKey -> rzKey.getColumn() == 2));
        assertTrue(rzKeys.subList(2, 4).stream().allMatch(rzKey -> rzKey.getColumn() == 3));
    }

    @Test
    void testRowSort() {
        //when
        final List<RzKey> rzKeys = new RzKeySelector()
                .withRowBetween(RzKey.RZKEY_1, RzKey.RZKEY_Q)
                .withColumnBetween(RzKey.RZKEY_1, RzKey.RZKEY_2)
                .sortedByRow().asList();

        //then
        assertFalse(rzKeys.isEmpty());
        assertEquals(4, rzKeys.size());
        assertTrue(rzKeys.subList(0, 2).stream().allMatch(rzKey -> rzKey.getRow() == 1));
        assertTrue(rzKeys.subList(2, 4).stream().allMatch(rzKey -> rzKey.getRow() == 2));
    }
}