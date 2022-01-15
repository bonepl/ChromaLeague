package net.booone.razersdk.sdk;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProgressiveRzKeySelectorTest {

    @Test
    void testJoinSets() {
        //given
        Set<RzKey> set1 = Set.of(RzKey.RZKEY_Q, RzKey.RZKEY_A, RzKey.RZKEY_Z);
        Set<RzKey> set2 = Set.of(RzKey.RZKEY_Q, RzKey.RZKEY_R);

        //when
        Set<RzKey> actual = joinSets(set1, set2);

        //then
        assertEquals(Set.of(RzKey.RZKEY_Q, RzKey.RZKEY_A, RzKey.RZKEY_Z, RzKey.RZKEY_R), actual);
    }

    @Test
    void testIllegalLengthException() {
        //given
        List<Set<RzKey>> testSet = List.of(Set.of(RzKey.RZKEY_ENTER));

        //when
        assertThrows(IllegalArgumentException.class, () -> new ProgressiveRzKeySelector(testSet, -1));
        assertThrows(IllegalArgumentException.class, () -> new ProgressiveRzKeySelector(testSet, 0));
        assertThrows(IllegalArgumentException.class, () -> new ProgressiveRzKeySelector(testSet, 2));
    }

    @Test
    void testIllegalPartsException() {
        //when
        assertThrows(IllegalArgumentException.class, () -> new ProgressiveRzKeySelector(null));
        assertThrows(IllegalArgumentException.class, () -> new ProgressiveRzKeySelector(Collections.emptyList()));
    }

    @Test
    void testLength1Progression() {
        //given
        Set<RzKey> set1 = Set.of(RzKey.RZKEY_Q, RzKey.RZKEY_A, RzKey.RZKEY_Z);
        RzKey[] array2 = new RzKey[]{RzKey.RZKEY_W, RzKey.RZKEY_S};
        RzKey rzKey3 = RzKey.RZKEY_E;

        //when
        ProgressiveRzKeySelector progressiveRzKeySelector = new ProgressiveRzKeySelector.Builder()
                .addPart(set1)
                .addPart(array2)
                .addPart(rzKey3)
                .build();

        //then
        assertEquals(set1, progressiveRzKeySelector.getNextPart());
        assertEquals(Arrays.stream(array2).collect(Collectors.toSet()), progressiveRzKeySelector.getNextPart());
        assertEquals(Set.of(rzKey3), progressiveRzKeySelector.getNextPart());

        assertEquals(set1, progressiveRzKeySelector.getNextPart());
        assertEquals(Arrays.stream(array2).collect(Collectors.toSet()), progressiveRzKeySelector.getNextPart());
        assertEquals(Set.of(rzKey3), progressiveRzKeySelector.getNextPart());
    }

    @Test
    void testLength2Progression() {
        //given
        Set<RzKey> set1 = Set.of(RzKey.RZKEY_Q, RzKey.RZKEY_A, RzKey.RZKEY_Z);
        Set<RzKey> set2 = Set.of(RzKey.RZKEY_W, RzKey.RZKEY_S);
        Set<RzKey> set3 = Set.of(RzKey.RZKEY_E);

        //when
        ProgressiveRzKeySelector progressiveRzKeySelector = new ProgressiveRzKeySelector.Builder()
                .addPart(set1)
                .addPart(set2)
                .addPart(set3)
                .withLength(2)
                .build();

        //then
        assertEquals(set1, progressiveRzKeySelector.getNextPart());
        assertEquals(joinSets(set1, set2), progressiveRzKeySelector.getNextPart());
        assertEquals(joinSets(set2, set3), progressiveRzKeySelector.getNextPart());
        assertEquals(set3, progressiveRzKeySelector.getNextPart());

        assertEquals(set1, progressiveRzKeySelector.getNextPart());
        assertEquals(joinSets(set1, set2), progressiveRzKeySelector.getNextPart());
        assertEquals(joinSets(set2, set3), progressiveRzKeySelector.getNextPart());
        assertEquals(set3, progressiveRzKeySelector.getNextPart());
    }

    @Test
    void testLength3Progression() {
        //given
        Set<RzKey> set1 = Set.of(RzKey.RZKEY_Q, RzKey.RZKEY_A, RzKey.RZKEY_Z);
        Set<RzKey> set2 = Set.of(RzKey.RZKEY_W, RzKey.RZKEY_S);
        Set<RzKey> set3 = Set.of(RzKey.RZKEY_E);

        //when
        ProgressiveRzKeySelector progressiveRzKeySelector = new ProgressiveRzKeySelector(List.of(set1, set2, set3), 3);

        //then
        assertEquals(set1, progressiveRzKeySelector.getNextPart());
        assertEquals(joinSets(set1, set2), progressiveRzKeySelector.getNextPart());
        assertEquals(joinSets(set1, set2, set3), progressiveRzKeySelector.getNextPart());
        assertEquals(joinSets(set2, set3), progressiveRzKeySelector.getNextPart());
        assertEquals(set3, progressiveRzKeySelector.getNextPart());

        assertEquals(set1, progressiveRzKeySelector.getNextPart());
        assertEquals(joinSets(set1, set2), progressiveRzKeySelector.getNextPart());
        assertEquals(joinSets(set1, set2, set3), progressiveRzKeySelector.getNextPart());
        assertEquals(joinSets(set2, set3), progressiveRzKeySelector.getNextPart());
        assertEquals(set3, progressiveRzKeySelector.getNextPart());
    }

    @SafeVarargs
    static Set<RzKey> joinSets(Set<RzKey>... sets) {
        return Stream.of(sets).flatMap(Set::stream).collect(Collectors.toSet());
    }
}