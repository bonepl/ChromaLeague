import {Color} from '../color/Color.js';
import {StaticColor} from '../color/StaticColor.js';
import {getRzKeyValues, RzKey} from '../sdk/RzKey.js';
import {Frame} from './Frame.js';
import {IFrame} from './IFrame.js';

/**
 * Single animation frame with one Frame
 */
export class SimpleFrame implements IFrame {
    private readonly keysToColors: Map<RzKey, Color> = new Map();
    private _hasFrame = true;

    constructor();
    constructor(color: Color);
    constructor(rzKey: RzKey, color: Color);
    constructor(rzKeys: readonly RzKey[], color: Color);
    constructor(keysToColors: Map<RzKey, Color>);
    constructor(
        colorOrKeyOrKeysOrMap?: Color | RzKey | readonly RzKey[] | Map<RzKey, Color>,
        color?: Color
    ) {
        if (colorOrKeyOrKeysOrMap === undefined) {
            // empty frame
        } else if (colorOrKeyOrKeysOrMap instanceof Map) {
            for (const [key, val] of colorOrKeyOrKeysOrMap) {
                this.keysToColors.set(key, val);
            }
        } else if (Array.isArray(colorOrKeyOrKeysOrMap)) {
            const nextColor: StaticColor = color!.getColor();
            for (const rzKey of colorOrKeyOrKeysOrMap) {
                this.keysToColors.set(rzKey, nextColor);
            }
        } else if (typeof colorOrKeyOrKeysOrMap === 'number' && color !== undefined) {
            // RzKey (number enum) + Color
            this.keysToColors.set(colorOrKeyOrKeysOrMap as RzKey, color);
        } else {
            // Single Color — fill all keys
            const c = colorOrKeyOrKeysOrMap as Color;
            for (const rzKey of getRzKeyValues()) {
                this.keysToColors.set(rzKey, c);
            }
        }
    }

    hasFrame(): boolean {
        return this._hasFrame;
    }

    pollFrame(): Frame {
        if (this._hasFrame) {
            this._hasFrame = false;
            return new Frame(this.keysToColors);
        }
        throw new Error('Animation does not have any frames to return');
    }
}
