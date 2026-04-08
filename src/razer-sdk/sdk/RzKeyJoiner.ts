import {RzKey} from './RzKey.js';
import {RzKeySelector} from './RzKeySelector.js';

/**
 * Helper class for joining RzKey objects and collections.
 *
 * Usage example:
 *   const selectedRzKeys = new RzKeyJoiner()
 *       .withKeys(RzKey.RZKEY_ESC)
 *       .withCollection([RzKey.RZKEY_A, RzKey.RZKEY_S])
 *       .withSelector(new RzKeySelector().withRow(0))
 *       .join();
 */
export class RzKeyJoiner {
    private readonly rzKeys = new Set<RzKey>();

    /** Add provided keys to underlying collection. */
    withKeys(...keys: RzKey[]): this {
        for (const key of keys) {
            this.rzKeys.add(key);
        }
        return this;
    }

    /** Add all keys from provided collection to underlying collection. */
    withCollection(collection: Iterable<RzKey>): this {
        for (const key of collection) {
            this.rzKeys.add(key);
        }
        return this;
    }

    /** Add all keys from provided RzKeySelector to underlying collection. */
    withSelector(rzKeySelector: RzKeySelector): this {
        for (const key of rzKeySelector.asSet()) {
            this.rzKeys.add(key);
        }
        return this;
    }

    /** Returns an unmodifiable copy of the underlying collection. */
    join(): ReadonlySet<RzKey> {
        return new Set(this.rzKeys);
    }
}
