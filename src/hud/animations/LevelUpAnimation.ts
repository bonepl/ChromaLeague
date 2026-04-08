import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {Animation} from '../../razer-sdk/animation/Animation.js';
import {Frame} from '../../razer-sdk/animation/Frame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {ProgressiveRzKeySelectorBuilder} from '../../razer-sdk/sdk/ProgressiveRzKeySelector.js';
import {RzKey} from '../../razer-sdk/sdk/RzKey.js';
import * as GameStateHelper from '../../state/GameStateHelper.js';

export class LevelUpAnimation extends Animation {
    private previousLevel = 0;

    pollFrame(): Frame {
        this.addLevelUpAnimationIfNeeded();
        if (this.hasFrame()) {
            return super.pollFrame();
        }
        return new SimpleFrame().pollFrame();
    }

    private addLevelUpAnimationIfNeeded(): void {
        if (GameStateHelper.getLevel() > this.previousLevel) {
            this.levelUp();
            this.previousLevel = GameStateHelper.getLevel();
        }
    }

    levelUp(): void {
        const levelUpSelector = new ProgressiveRzKeySelectorBuilder()
            .addPartFromKeys(RzKey.RZKEY_MACRO5, RzKey.RZKEY_LCTRL, RzKey.RZKEY_LWIN)
            .addPartFromKeys(RzKey.RZKEY_MACRO4, RzKey.RZKEY_LSHIFT)
            .addPartFromKeys(RzKey.RZKEY_MACRO3, RzKey.RZKEY_CAPSLOCK)
            .addPartFromKeys(RzKey.RZKEY_MACRO2, RzKey.RZKEY_TAB)
            .addPartFromKeys(RzKey.RZKEY_MACRO1, RzKey.RZKEY_TILDE)
            .addPartFromKeys(RzKey.RZKEY_ESC)
            .withLength(3)
            .build();
        const frame = new AnimatedFrame();
        for (let i = 0; i < levelUpSelector.getTotalSteps(); i++) {
            frame.addAnimationFrame(2, new SimpleFrame([...levelUpSelector.getNextPart()], StaticColor.YELLOW));
        }
        this.addToFront(frame);
    }
}
