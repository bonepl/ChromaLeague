import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {Color} from '../../razer-sdk/color/Color.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';

const BLINK_TIMES = 8;

export class StaticBlinkingAnimation extends AnimatedFrame {
    constructor(color: Color) {
        super();
        for (let i = 0; i < BLINK_TIMES; i++) {
            this.addAnimationFrame(new SimpleFrame(color));
            this.addAnimationFrame(3, new SimpleFrame(StaticColor.BLACK));
        }
    }
}
