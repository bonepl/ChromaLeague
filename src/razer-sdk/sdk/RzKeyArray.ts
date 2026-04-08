import {getColumn, getRow, getRzKeyValues, RzKey} from './RzKey.js';

const MAX_ROW = 5;
const MAX_COLUMN = 21;

const RZ_KEY_ARRAY: (RzKey | undefined)[][] = Array.from(
    { length: MAX_ROW + 1 },
    () => new Array<RzKey | undefined>(MAX_COLUMN + 1).fill(undefined),
);

// Static initialization - populate the lookup array
for (const rk of getRzKeyValues()) {
    RZ_KEY_ARRAY[getRow(rk)][getColumn(rk)] = rk;
}

export function keyAt(row: number, column: number): RzKey | undefined {
    if (row > MAX_ROW || row < 0 || column > MAX_COLUMN || column < 0) {
        return undefined;
    }
    return RZ_KEY_ARRAY[row][column];
}
