import {Color} from '../color/Color.js';
import {StaticColor} from '../color/StaticColor.js';
import {RzKey} from '../sdk/RzKey.js';
import {Frame} from './Frame.js';
import {IFrame} from './IFrame.js';

/**
 * Simulates layering of multiple Frames to create one Frame.
 * Newest frame always overwrites previous ones.
 * Keys with color StaticColor.NONE are treated as transparent.
 */
export class LayeredFrame implements IFrame {
    private readonly keysToColors: Map<RzKey, Color> = new Map();
    private _hasFrame = true;

    /** Add next frame layered on top of previous frames */
    addFrame(frame: IFrame): void {
        for (const [key, color] of frame.pollFrame().getKeysToColors()) {
            if (color !== StaticColor.NONE) {
                this.keysToColors.set(key, color);
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
        throw new Error('LayeredFrame does not have any frames to return');
    }
}
