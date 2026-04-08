import {getColumn, getRow, getRzKeyValues, RzKey} from './RzKey.js';

export const KEYBOARD_COLUMNS = 22;
export const KEYBOARD_ROWS = 6;

/**
 * Helper class for selecting RzKey sets.
 *
 * Usage example:
 *   const selectedKeys = new RzKeySelector()
 *       .withColumnBetween(RzKey.RZKEY_A, RzKey.RZKEY_D)
 *       .withRowBetween(RzKey.RZKEY_A, RzKey.RZKEY_Z)
 *       .sortedByColumn()
 *       .asList();
 *   // returns A, Z, S, X, D, C
 */
export class RzKeySelector {
    private columnSelector: (column: number) => boolean = () => true;
    private rowSelector: (row: number) => boolean = () => true;
    private sort: (a: RzKey, b: RzKey) => number = (a, b) => a - b;

    private withColumnPredicate(predicate: (column: number) => boolean): this {
        this.columnSelector = predicate;
        return this;
    }

    private withRowPredicate(predicate: (row: number) => boolean): this {
        this.rowSelector = predicate;
        return this;
    }

    private withSort(comparator: (a: RzKey, b: RzKey) => number): this {
        this.sort = comparator;
        return this;
    }

    /** Select keys with the same column as provided RzKey */
    withColumnOf(rzKey: RzKey): this {
        return this.withColumn(getColumn(rzKey));
    }

    /** Select keys with the same row as provided RzKey */
    withRowOf(rzKey: RzKey): this {
        return this.withRow(getRow(rzKey));
    }

    /** Select keys with the same column as provided number */
    withColumn(column: number): this {
        if (column < 0 || column >= KEYBOARD_COLUMNS) {
            throw new Error(`Column number should be between 0 and ${KEYBOARD_COLUMNS}, but was ${column}`);
        }
        return this.withColumnPredicate((col) => col === column);
    }

    /** Select keys with the same row as provided number */
    withRow(row: number): this {
        if (row < 0 || row >= KEYBOARD_ROWS) {
            throw new Error(`Row number should be between 0 and ${KEYBOARD_ROWS}, but was ${row}`);
        }
        return this.withRowPredicate((r) => r === row);
    }

    /** Select keys with the column between provided RzKeys */
    withColumnBetween(from: RzKey, to: RzKey): this {
        if (getColumn(from) > getColumn(to)) {
            throw new Error('Last column to select must be equal or bigger than first column to select');
        }
        return this.withColumnPredicate((column) => column >= getColumn(from) && column <= getColumn(to));
    }

    /** Select keys with the row between provided RzKeys */
    withRowBetween(from: RzKey, to: RzKey): this {
        if (getRow(from) > getRow(to)) {
            throw new Error('Last row to select must be equal or bigger than first row to select');
        }
        return this.withRowPredicate((row) => row >= getRow(from) && row <= getRow(to));
    }

    /** Select keys as the rectangle between provided RzKeys */
    withRectangleBetween(topLeft: RzKey, bottomRight: RzKey): this {
        return this.withRowBetween(topLeft, bottomRight).withColumnBetween(topLeft, bottomRight);
    }

    /** Sort selected keys by column number (applies only when selecting asList()) */
    sortedByColumn(): this {
        return this.withSort((a, b) => getColumn(a) - getColumn(b));
    }

    /** Sort selected keys by row number (applies only when selecting asList()) */
    sortedByRow(): this {
        return this.withSort((a, b) => getRow(a) - getRow(b));
    }

    /** Collect selected RzKeys to Set */
    asSet(): Set<RzKey> {
        return new Set(this.asStream());
    }

    /** Collect selected RzKeys to sorted List */
    asList(): RzKey[] {
        return this.asStream().sort(this.sort);
    }

    private asStream(): RzKey[] {
        return getRzKeyValues()
            .filter((rzKey) => this.columnSelector(getColumn(rzKey)))
            .filter((rzKey) => this.rowSelector(getRow(rzKey)));
    }
}
