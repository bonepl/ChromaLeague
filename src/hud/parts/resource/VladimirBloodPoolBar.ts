import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../../razer-sdk/animation/Frame.js';
import {StaticColor} from '../../../razer-sdk/color/StaticColor.js';
import {ProgressBar} from '../ProgressBar.js';
import {getResourceBarKeys} from './ResourceBars.js';
import {getResourcePercentage} from '../../../state/GameStateHelper.js';

export class VladimirBloodPoolBar extends AnimatedFrame {
    private previousResourcePercentage = 0;

    pollFrame(): Frame {
        const currentResourcePercentage = getResourcePercentage();
        if (currentResourcePercentage > this.previousResourcePercentage) {
            if (currentResourcePercentage < 50) {
                this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), currentResourcePercentage, StaticColor.WHITE));
            } else {
                this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), currentResourcePercentage, StaticColor.YELLOW));
            }
        } else {
            this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), currentResourcePercentage, StaticColor.RED));
        }
        this.previousResourcePercentage = currentResourcePercentage;
        return super.pollFrame();
    }
}
