import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {StaticBlinkingAnimation} from './StaticBlinkingAnimation.js';

export class AllyBaronKillAnimation extends StaticBlinkingAnimation {
    constructor() {
        super(StaticColor.PURPLE);
    }
}
