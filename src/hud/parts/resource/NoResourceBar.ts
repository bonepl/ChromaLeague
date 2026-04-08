import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../../razer-sdk/animation/Frame.js';
import {SimpleFrame} from '../../../razer-sdk/animation/SimpleFrame.js';

export class NoResourceBar extends AnimatedFrame {
    pollFrame(): Frame {
        this.addAnimationFrame(new SimpleFrame());
        return super.pollFrame();
    }
}
