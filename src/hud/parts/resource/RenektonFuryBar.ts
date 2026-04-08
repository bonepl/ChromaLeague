import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../../razer-sdk/animation/Frame.js';
import {StaticColor} from '../../../razer-sdk/color/StaticColor.js';
import {ProgressBar} from '../ProgressBar.js';
import {getResourceBarKeys} from './ResourceBars.js';
import {getResourcePercentage} from '../../../state/GameStateHelper.js';

export class RenektonFuryBar extends AnimatedFrame {
    pollFrame(): Frame {
        const resourcePercentage = getResourcePercentage();
        if (resourcePercentage < 50) {
            this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), resourcePercentage, StaticColor.WHITE));
        } else {
            this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), resourcePercentage, StaticColor.RED));
        }
        return super.pollFrame();
    }
}
