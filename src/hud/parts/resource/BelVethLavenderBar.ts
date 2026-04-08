import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../../razer-sdk/animation/Frame.js';
import {StaticColor} from '../../../razer-sdk/color/StaticColor.js';
import {ProgressBar} from '../ProgressBar.js';
import {getResourceBarKeys} from './ResourceBars.js';
import {getResourcePercentage} from '../../../state/GameStateHelper.js';

export class BelVethLavenderBar extends AnimatedFrame {
    pollFrame(): Frame {
        this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), getResourcePercentage(), StaticColor.PURPLE));
        return super.pollFrame();
    }
}
