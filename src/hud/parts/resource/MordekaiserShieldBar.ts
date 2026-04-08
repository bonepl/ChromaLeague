import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../../razer-sdk/animation/Frame.js';
import {StaticColor} from '../../../razer-sdk/color/StaticColor.js';
import {ProgressBar} from '../ProgressBar.js';
import {getResourceBarKeys} from './ResourceBars.js';
import {getResourcePercentage} from '../../../state/GameStateHelper.js';

export class MordekaiserShieldBar extends AnimatedFrame {
    pollFrame(): Frame {
        this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), getResourcePercentage(), StaticColor.WHITE));
        return super.pollFrame();
    }
}
