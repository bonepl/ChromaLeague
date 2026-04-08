import {Animation} from '../../../razer-sdk/animation/Animation.js';
import {Frame} from '../../../razer-sdk/animation/Frame.js';
import {IFrame} from '../../../razer-sdk/animation/IFrame.js';
import {RzKey} from '../../../razer-sdk/sdk/RzKey.js';
import {RzKeySelector} from '../../../razer-sdk/sdk/RzKeySelector.js';
import * as GameStateHelper from '../../../state/GameStateHelper.js';
import {RunningState} from '../../../state/RunningState.js';
import {CLColor} from '../../colors/CLColor.js';
import {indexToFill, ProgressBar} from '../ProgressBar.js';
import {GainedHealthAnimation} from './GainedHealthAnimation.js';
import {LostHealthAnimation} from './LostHealthAnimation.js';

const HP_BAR_KEYS: RzKey[] = new RzKeySelector()
    .withRowOf(RzKey.RZKEY_ESC)
    .withColumnBetween(RzKey.RZKEY_ESC, RzKey.RZKEY_F12)
    .sortedByColumn()
    .asList();

export class HealthBar extends Animation {
    private previousHp = 0;

    override pollFrame(): Frame {
        const currentHp = RunningState.getGameState().activePlayer!.championStats.currentHealth;
        this.addToBack(getHpBar());
        if (currentHp < this.previousHp) {
            this.addToFront(new LostHealthAnimation(this.previousHp, currentHp));
        }
        if (currentHp > this.previousHp) {
            this.addToFront(new GainedHealthAnimation(this.previousHp, currentHp));
        }
        this.previousHp = currentHp;
        return super.pollFrame();
    }
}

function getHpBar(): IFrame {
    return new ProgressBar(HP_BAR_KEYS, GameStateHelper.getHpPercentage(), CLColor.HEALTH);
}

export function getHealthBarKeys(): RzKey[] {
    return [...HP_BAR_KEYS];
}

export function getHpBarPart(previousHp: number, currentHp: number): RzKey[] {
    const maxHealth = RunningState.getGameState().activePlayer!.championStats.maxHealth;
    const from = indexToFill(getHealthBarKeys().length, Math.trunc(previousHp * 100 / maxHealth));
    const to = indexToFill(getHealthBarKeys().length, Math.trunc(currentHp * 100 / maxHealth));
    return getHealthBarKeys().slice(from, to);
}
