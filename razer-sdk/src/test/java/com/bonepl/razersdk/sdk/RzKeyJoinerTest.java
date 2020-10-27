package com.bonepl.razersdk.sdk;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RzKeyJoinerTest {
    @Test
    void testSimpleJoin() {
        //when
        final List<RzKey> rzKeys = new RzKeyJoiner()
                .with(RzKey.RZKEY_A).with(RzKey.RZKEY_B).with(RzKey.RZKEY_A).join();

        //then
        assertFalse(rzKeys.isEmpty());
        assertEquals(2, rzKeys.size());
        assertTrue(rzKeys.containsAll(List.of(RzKey.RZKEY_A, RzKey.RZKEY_B)));
    }

    @Test
    void testArrayJoin() {
        //when
        final List<RzKey> rzKeys = new RzKeyJoiner()
                .with(RzKey.RZKEY_A, RzKey.RZKEY_B).with(RzKey.RZKEY_A, RzKey.RZKEY_C).join();

        //then
        assertFalse(rzKeys.isEmpty());
        assertEquals(3, rzKeys.size());
        assertTrue(rzKeys.containsAll(List.of(RzKey.RZKEY_A, RzKey.RZKEY_B, RzKey.RZKEY_C)));
    }

    @Test
    void testEmptyJoins() {
        //when
        final List<RzKey> rzKeys = new RzKeyJoiner().with()
                .with(Collections.emptyList())
                .with(new RzKeySelector()).join();

        //then
        assertTrue(rzKeys.isEmpty());
    }

    @Test
    void testCollectionJoin() {
        //when
        final List<RzKey> rzkeys1 = List.of(RzKey.RZKEY_A, RzKey.RZKEY_A);
        final Set<RzKey> rzKeys2 = Collections.singleton(RzKey.RZKEY_B);
        final List<RzKey> rzKeys3 = Collections.singletonList(RzKey.RZKEY_B);

        //when
        final List<RzKey> rzKeys = new RzKeyJoiner()
                .with(rzkeys1).with(rzKeys2).with(rzKeys3).join();

        //then
        assertFalse(rzKeys.isEmpty());
        assertEquals(2, rzKeys.size());
        assertTrue(rzKeys.containsAll(List.of(RzKey.RZKEY_A, RzKey.RZKEY_B)));
    }

    @Test
    void testKeySelectorJoin() {
        //when
        final RzKeySelector rzKeySelector = new RzKeySelector()
                .withRowOf(RzKey.RZKEY_1).withColumnBetween(RzKey.RZKEY_1, RzKey.RZKEY_2);
        final RzKeySelector rzKeySelector2 = new RzKeySelector()
                .withRowBetween(RzKey.RZKEY_1, RzKey.RZKEY_Q).withColumnOf(RzKey.RZKEY_1);

        //then
        final List<RzKey> rzKeys = new RzKeyJoiner()
                .with(rzKeySelector).with(rzKeySelector2).join();

        //then
        //then
        assertFalse(rzKeys.isEmpty());
        assertEquals(3, rzKeys.size());
        assertTrue(rzKeys.containsAll(List.of(RzKey.RZKEY_1, RzKey.RZKEY_2, RzKey.RZKEY_Q)));
    }
}