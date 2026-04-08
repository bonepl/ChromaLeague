import {Frame} from './Frame.js';
import {IFrame} from './IFrame.js';

/**
 * Object containing a sequence of Frames that build the animation.
 * Each following Frame is polled by calling pollFrame().
 * Speed of the animation depends on the external scheduler calling pollFrame().
 */
export class AnimatedFrame implements IFrame {
    private readonly frames: Frame[] = [];

    /** Add single animation frame */
    addAnimationFrame(frame: IFrame): void;
    /** Add single animation frame extended by frameCount */
    addAnimationFrame(frameCount: number, frame: IFrame): void;
    addAnimationFrame(frameCountOrFrame: number | IFrame, frame?: IFrame): void {
        let frameCount: number;
        let actualFrame: IFrame;

        if (typeof frameCountOrFrame === 'number') {
            frameCount = frameCountOrFrame;
            actualFrame = frame!;
        } else {
            frameCount = 1;
            actualFrame = frameCountOrFrame;
        }

        if (frameCount > 0) {
            const animationFrame = actualFrame.pollFrame();
            for (let i = 0; i < frameCount; i++) {
                this.frames.push(animationFrame);
            }
        }
    }

    hasFrame(): boolean {
        return this.frames.length > 0;
    }

    pollFrame(): Frame {
        if (this.hasFrame()) {
            return this.frames.shift()!;
        }
        throw new Error('AnimatedFrame does not have any frames to return');
    }

    /** Clear the frame sequence, and stop the animation */
    clearFrames(): void {
        this.frames.length = 0;
    }
}
