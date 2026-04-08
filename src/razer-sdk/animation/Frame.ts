import {Color} from '../color/Color.js';
import {RzKey} from '../sdk/RzKey.js';

/**
 * Frame object containing map of keys to corresponding colors
 */
export class Frame {
    private readonly keysToColors: Map<RzKey, Color>;

    constructor(keysToColors: Map<RzKey, Color>) {
        this.keysToColors = new Map(keysToColors);
    }

    /** Return the underlying map of keys to colors (as a copy) */
    getKeysToColors(): ReadonlyMap<RzKey, Color> {
        return this.keysToColors;
    }
}
