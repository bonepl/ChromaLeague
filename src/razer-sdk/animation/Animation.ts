import {Color} from '../color/Color.js';
import {RzKey} from '../sdk/RzKey.js';
import {Frame} from './Frame.js';
import {IFrame} from './IFrame.js';
import {LayeredFrame} from './LayeredFrame.js';
import {SimpleFrame} from './SimpleFrame.js';

/**
 * Top level class for advanced animations.
 * Provides layering of animations as well as defining
 * frames in front or back of the animation.
 *
 * After creating an animation with pollFrame(), any IFrame
 * whose hasFrame() returns false will be removed from the sequence.
 *
 * Every SimpleFrame or LayeredFrame added to Animation
 * will be removed after the first call to pollFrame().
 * To preserve a static effect, extend this class and add
 * static frames in an overridden pollFrame().
 */
export class Animation implements IFrame {
    private readonly frames: IFrame[] = [];

    /** Add IFrame to the front of the animation (rendered on top / last) */
    addToFront(frame: IFrame): void {
        this.frames.push(frame);
    }

    /** Add IFrame to the back of the animation (rendered first / bottom) */
    addToBack(frame: IFrame): void {
        this.frames.unshift(frame);
    }

    hasFrame(): boolean {
        return this.frames.length > 0;
    }

    pollFrame(): Frame {
        if (this.hasFrame()) {
            const layeredFrame = new LayeredFrame();
            for (const frame of this.frames) {
                if (frame.hasFrame()) {
                    const f = frame.pollFrame();
                    layeredFrame.addFrame(new SimpleFrame(f.getKeysToColors() as Map<RzKey, Color>));
                }
            }
            // Remove exhausted frames
            for (let i = this.frames.length - 1; i >= 0; i--) {
                if (!this.frames[i].hasFrame()) {
                    this.frames.splice(i, 1);
                }
            }
            return layeredFrame.pollFrame();
        }
        throw new Error('Animation does not have any frames to return');
    }
}
