import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../../razer-sdk/animation/Frame.js';
import {CLColor} from '../../colors/CLColor.js';
import {ProgressBar} from '../ProgressBar.js';
import {getResourceBarKeys} from './ResourceBars.js';
import {getResourcePercentage} from '../../../state/GameStateHelper.js';

export class ManaBar extends AnimatedFrame {
    pollFrame(): Frame {
        this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), getResourcePercentage(), CLColor.MANA));
        return super.pollFrame();
    }
}
