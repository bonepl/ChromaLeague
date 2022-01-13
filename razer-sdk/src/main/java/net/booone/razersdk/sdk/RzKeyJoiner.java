package net.booone.razersdk.sdk;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Helper class for joining {@link RzKey} objects and collections. <br><br>
 * Usage example:<br>
 * <pre>
 * Set<RzKey> selectedRzKeys = new RzKeyJoiner()
 *     .with(RzKey.RZKEY_ESC)
 *     .with(Arrays.asList(RzKey.RZKEY_A, RzKey.RZKEY_S))
 *     .with(new RzKeySelector().withRow(0))
 *     .join();
 * //selectedRzKeys contains ESC, A, S and all keys from first top row
 * </pre>
 */
public final class RzKeyJoiner {
    private final EnumSet<RzKey> rzKeys = EnumSet.noneOf(RzKey.class);

    /**
     * Add provided keys to underlying collection.
     *
     * @param array keys to add
     * @return this
     */
    public RzKeyJoiner with(RzKey... array) {
        if (array.length != 0) {
            rzKeys.addAll(Set.of(array));
        }
        return this;
    }

    /**
     * Add all keys from provided collection to underlying collection.
     *
     * @param collection keys to add
     * @return this
     */
    public RzKeyJoiner with(Collection<RzKey> collection) {
        rzKeys.addAll(collection);
        return this;
    }

    /**
     * Add all keys from provided {@link RzKeySelector} to underlying collection.
     *
     * @param rzKeySelector keys to add
     * @return this
     */
    public RzKeyJoiner with(RzKeySelector rzKeySelector) {
        rzKeys.addAll(rzKeySelector.asSet());
        return this;
    }

    /**
     * @return underlying collection
     */
    public Set<RzKey> join() {
        return Collections.unmodifiableSet(rzKeys);
    }
}
