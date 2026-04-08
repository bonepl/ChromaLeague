import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {LayeredFrame} from '../../razer-sdk/animation/LayeredFrame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {RzKey} from '../../razer-sdk/sdk/RzKey.js';
import {getKillingSpreeBar} from '../parts/AssistKillingSpreeBar.js';
import {getCurrentBackgroundColor} from '../parts/Background.js';

export class ActivePlayerAssistAnimation extends AnimatedFrame {
    constructor() {
        super();
        for (let i = 0; i < 4; i++) {
            this.addAnimationFrame(2, withBackground([RzKey.RZKEY_O, RzKey.RZKEY_L, RzKey.RZKEY_DOT]));
            this.addAnimationFrame(2, withBackground([RzKey.RZKEY_K, RzKey.RZKEY_L, RzKey.RZKEY_SEMICOLON]));
            this.addAnimationFrame(2, withBackground([RzKey.RZKEY_M, RzKey.RZKEY_L, RzKey.RZKEY_SQUARE_BRACKET_LEFT]));
            this.addAnimationFrame(2, withBackground([RzKey.RZKEY_COMA, RzKey.RZKEY_L, RzKey.RZKEY_P]));
        }
    }
}

function withBackground(rzKeys: RzKey[]): LayeredFrame {
    const layeredFrame = new LayeredFrame();
    layeredFrame.addFrame(new SimpleFrame(getKillingSpreeBar(), getCurrentBackgroundColor()));
    layeredFrame.addFrame(new SimpleFrame(rzKeys, StaticColor.YELLOW));
    return layeredFrame;
}
