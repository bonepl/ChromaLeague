import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {StaticPartialBlinkingAnimation} from './StaticPartialBlinkingAnimation.js';

export class EnemyBaronKillAnimation extends StaticPartialBlinkingAnimation {
    constructor() {
        super(StaticColor.PURPLE);
    }
}
