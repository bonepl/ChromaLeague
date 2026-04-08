import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../razer-sdk/animation/Frame.js';
import {IFrame} from '../../razer-sdk/animation/IFrame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';

export class EventAnimation extends AnimatedFrame {
    addAnimation(frame: IFrame): void {
        while (frame.hasFrame()) {
            this.addAnimationFrame(frame);
        }
    }

    override pollFrame(): Frame {
        if (this.hasFrame()) {
            return super.pollFrame();
        }
        return new SimpleFrame().pollFrame();
    }
}
