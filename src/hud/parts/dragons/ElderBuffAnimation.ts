import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../../razer-sdk/animation/Frame.js';
import {SimpleFrame} from '../../../razer-sdk/animation/SimpleFrame.js';
import {StaticColor} from '../../../razer-sdk/color/StaticColor.js';
import {RzKey} from '../../../razer-sdk/sdk/RzKey.js';
import {RzKeyJoiner} from '../../../razer-sdk/sdk/RzKeyJoiner.js';
import {FOURTH_DRAGON_ROW, SOUL_BAR} from './KilledDragonsBar.js';

export class ElderBuffAnimation extends AnimatedFrame {
    override pollFrame(): Frame {
        if (!this.hasFrame()) {
            this.extendAnimation();
        }
        return super.pollFrame();
    }

    private extendAnimation(): void {
        this.addAnimationFrame(2, new SimpleFrame(
            [...new RzKeyJoiner()
                .withCollection(FOURTH_DRAGON_ROW)
                .withKeys(RzKey.RZKEY_RALT)
                .withCollection(SOUL_BAR)
                .withKeys(RzKey.RZKEY_LEFT, RzKey.RZKEY_DOWN, RzKey.RZKEY_RIGHT)
                .join()],
            StaticColor.WHITE,
        ));
        this.addAnimationFrame(2, new SimpleFrame(
            [RzKey.RZKEY_RSHIFT, RzKey.RZKEY_UP, RzKey.RZKEY_SLASH],
            StaticColor.WHITE,
        ));
        this.addAnimationFrame(2, new SimpleFrame(
            [RzKey.RZKEY_ENTER, RzKey.RZKEY_APOSTROPHE],
            StaticColor.WHITE,
        ));
        this.addAnimationFrame(2, new SimpleFrame(
            [RzKey.RZKEY_BACKSLASH, RzKey.RZKEY_SQUARE_BRACKET_RIGHT],
            StaticColor.WHITE,
        ));
        this.addAnimationFrame(20, new SimpleFrame());
    }
}
