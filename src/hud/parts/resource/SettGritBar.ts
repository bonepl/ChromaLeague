import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../../razer-sdk/animation/Frame.js';
import {Color} from '../../../razer-sdk/color/Color.js';
import {StaticColor} from '../../../razer-sdk/color/StaticColor.js';
import {BackgroundBreathingColor} from '../../colors/BackgroundBreathingColor.js';
import {ProgressBar} from '../ProgressBar.js';
import {getResourceBarKeys} from './ResourceBars.js';
import {getResourcePercentage} from '../../../state/GameStateHelper.js';

const GRIT_BAR = new BackgroundBreathingColor(StaticColor.WHITE, 10);

export class SettGritBar extends AnimatedFrame {
    pollFrame(): Frame {
        const resourcePercentage = getResourcePercentage();
        if (resourcePercentage >= 90) {
            GRIT_BAR.setUpColor(StaticColor.YELLOW);
        } else {
            GRIT_BAR.setUpColor(StaticColor.WHITE);
        }
        let color: Color;
        if (resourcePercentage >= 40 && resourcePercentage < 80) {
            GRIT_BAR.setSteps(10);
            color = GRIT_BAR;
        } else if (resourcePercentage >= 80) {
            GRIT_BAR.setSteps(5);
            color = GRIT_BAR;
        } else {
            color = StaticColor.WHITE;
        }
        this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), resourcePercentage, color));
        return super.pollFrame();
    }
}
