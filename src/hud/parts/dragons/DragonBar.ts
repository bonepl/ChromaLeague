import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../../razer-sdk/animation/Frame.js';
import {LayeredFrame} from '../../../razer-sdk/animation/LayeredFrame.js';
import * as GameStateHelper from '../../../state/GameStateHelper.js';
import {ElderBuffAnimation} from './ElderBuffAnimation.js';
import {KilledDragonsBar} from './KilledDragonsBar.js';

export class DragonBar extends AnimatedFrame {
    private readonly elderBuffAnimation = new ElderBuffAnimation();
    private readonly killedDragonsBar = new KilledDragonsBar();

    override pollFrame(): Frame {
        const layeredFrame = new LayeredFrame();
        layeredFrame.addFrame(this.killedDragonsBar);
        if (GameStateHelper.hasElderBuff()) {
            layeredFrame.addFrame(this.elderBuffAnimation);
        }
        this.addAnimationFrame(layeredFrame);
        return super.pollFrame();
    }
}
