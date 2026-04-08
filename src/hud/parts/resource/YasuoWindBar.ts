import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../../razer-sdk/animation/Frame.js';
import {Color} from '../../../razer-sdk/color/Color.js';
import {StaticColor} from '../../../razer-sdk/color/StaticColor.js';
import {BackgroundBreathingColor} from '../../colors/BackgroundBreathingColor.js';
import {ProgressBar} from '../ProgressBar.js';
import {getResourceBarKeys} from './ResourceBars.js';
import {getResourcePercentage} from '../../../state/GameStateHelper.js';

export const WIND_SHIELD_READY_COLOR = new BackgroundBreathingColor(StaticColor.WHITE);
export const WIND_SHIELD_COLOR = StaticColor.GRAY;

export class YasuoWindBar extends AnimatedFrame {
    pollFrame(): Frame {
        const resourcePercentage = getResourcePercentage();
        let color: Color;
        if (resourcePercentage === 100) {
            color = WIND_SHIELD_READY_COLOR;
        } else {
            color = WIND_SHIELD_COLOR;
        }
        this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), resourcePercentage, color));
        return super.pollFrame();
    }
}
