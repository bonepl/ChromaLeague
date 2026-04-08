import {ChromaNativeSDK} from '../src/razer-sdk/ChromaNativeSDK.js';
import {IFrame} from '../src/razer-sdk/animation/IFrame.js';

/**
 * Helper class for testing animations on actual hardware.
 * Uses native DLL for reliable communication with Razer Chroma SDK.
 */
export class AnimationTester {
    private beforeIterationAction?: (i: number) => void;
    private afterIterationAction?: (i: number) => void;
    private sleepTime: number = 50;
    private sleepAfter: number = 0;

    withBeforeIterationAction(action: (i: number) => void): this {
        this.beforeIterationAction = action;
        return this;
    }

    withAfterIterationAction(action: (i: number) => void): this {
        this.afterIterationAction = action;
        return this;
    }

    withSleepTime(milliseconds: number): this {
        this.sleepTime = milliseconds;
        return this;
    }

    withSleepAfter(milliseconds: number): this {
        this.sleepAfter = milliseconds;
        return this;
    }

    /**
     * Test animation until hasFrame returns false
     */
    async testAnimation(frame: IFrame, iterations?: number): Promise<void> {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();

        try {
            if (iterations !== undefined) {
                for (let i = 0; i < iterations; i++) {
                    this.beforeIterationAction?.(i);
                    await sdk.createKeyboardEffect(frame);
                    this.afterIterationAction?.(i);
                    await this.sleep(this.sleepTime);
                }
            } else {
                let i = 0;
                while (frame.hasFrame()) {
                    this.beforeIterationAction?.(i);
                    await sdk.createKeyboardEffect(frame);
                    this.afterIterationAction?.(i);
                    await this.sleep(this.sleepTime);
                    i++;
                }
            }

            await this.sleep(this.sleepAfter);
        } finally {
            await sdk.close();
        }
    }

    private sleep(milliseconds: number): Promise<void> {
        return new Promise(resolve => setTimeout(resolve, milliseconds));
    }
}
