import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../../razer-sdk/animation/Frame.js';
import {Color} from '../../../razer-sdk/color/Color.js';
import {StaticColor} from '../../../razer-sdk/color/StaticColor.js';
import {BackgroundBreathingColor} from '../../colors/BackgroundBreathingColor.js';
import {ProgressBar} from '../ProgressBar.js';
import {getResourceBarKeys} from './ResourceBars.js';
import {getResourcePercentage} from '../../../state/GameStateHelper.js';

const COURAGE_BAR = new BackgroundBreathingColor(StaticColor.YELLOW, 10);

export class KledCourageBar extends AnimatedFrame {
    pollFrame(): Frame {
        const resourcePercentage = getResourcePercentage();
        let color: Color;
        if (resourcePercentage >= 50 && resourcePercentage < 80) {
            COURAGE_BAR.setSteps(10);
            color = COURAGE_BAR;
        } else if (resourcePercentage >= 80 && resourcePercentage < 100) {
            COURAGE_BAR.setSteps(5);
            color = COURAGE_BAR;
        } else if (resourcePercentage === 100) {
            color = StaticColor.RED;
        } else {
            color = StaticColor.WHITE;
        }
        this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), resourcePercentage, color));
        return super.pollFrame();
    }
}
