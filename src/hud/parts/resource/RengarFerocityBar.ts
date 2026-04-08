import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../../razer-sdk/animation/Frame.js';
import {Color} from '../../../razer-sdk/color/Color.js';
import {StaticColor} from '../../../razer-sdk/color/StaticColor.js';
import {BackgroundBreathingColor} from '../../colors/BackgroundBreathingColor.js';
import {ProgressBar} from '../ProgressBar.js';
import {getResourceBarKeys} from './ResourceBars.js';
import {getResourcePercentage} from '../../../state/GameStateHelper.js';

const FEROCITY_FULL_COLOR = new BackgroundBreathingColor(StaticColor.YELLOW, 5);

export class RengarFerocityBar extends AnimatedFrame {
    pollFrame(): Frame {
        const resourcePercentage = getResourcePercentage();
        let color: Color;
        if (resourcePercentage === 100) {
            color = FEROCITY_FULL_COLOR;
        } else {
            color = StaticColor.WHITE;
        }
        this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), resourcePercentage, color));
        return super.pollFrame();
    }
}
