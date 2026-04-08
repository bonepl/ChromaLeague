import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {Animation} from '../../razer-sdk/animation/Animation.js';
import {Frame} from '../../razer-sdk/animation/Frame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {Color} from '../../razer-sdk/color/Color.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {RzKey} from '../../razer-sdk/sdk/RzKey.js';
import * as GameStateHelper from '../../state/GameStateHelper.js';
import {GoldColor} from '../colors/GoldColor.js';
import {
    FIRST_NUMPAD_COLUMN,
    FOURTH_NUMPAD_COLUMN,
    SECOND_NUMPAD_COLUMN,
    THIRD_NUMPAD_COLUMN,
} from '../PredefinedKeySets.js';
import {ProgressBar} from './ProgressBar.js';

export const GOLD_FULL = 3000;

export const GOLD_BAR_KEYS: Map<RzKey, Color> = new Map<RzKey, Color>([
    [RzKey.RZKEY_NUMPAD_DECIMAL, new GoldColor()],
    [RzKey.RZKEY_NUMPAD0, new GoldColor()],
    [RzKey.RZKEY_NUMPAD2, new GoldColor()],
    [RzKey.RZKEY_NUMPAD_ENTER, new GoldColor()],
    [RzKey.RZKEY_NUMPAD3, new GoldColor()],
    [RzKey.RZKEY_NUMPAD5, new GoldColor()],
    [RzKey.RZKEY_NUMPAD1, new GoldColor()],
    [RzKey.RZKEY_NUMPAD6, new GoldColor()],
    [RzKey.RZKEY_NUMPAD8, new GoldColor()],
    [RzKey.RZKEY_NUMPAD4, new GoldColor()],
    [RzKey.RZKEY_NUMPAD_ADD, new GoldColor()],
    [RzKey.RZKEY_NUMPAD9, new GoldColor()],
    [RzKey.RZKEY_NUMPAD_DIVIDE, new GoldColor()],
    [RzKey.RZKEY_NUMPAD7, new GoldColor()],
    [RzKey.RZKEY_NUMPAD_MULTIPLY, new GoldColor()],
    [RzKey.RZKEY_NUMLOCK, new GoldColor()],
    [RzKey.RZKEY_NUMPAD_SUBTRACT, new GoldColor()],
]);

const NUMPAD_COLUMNS: RzKey[][] = [
    FIRST_NUMPAD_COLUMN,
    SECOND_NUMPAD_COLUMN,
    THIRD_NUMPAD_COLUMN,
    FOURTH_NUMPAD_COLUMN,
];

export class GoldBar extends Animation {
    private readonly goldDiffToSpawnCoin: number;
    private readonly millisecondsForGoldCount: number;

    private lastGold: number | null = null;
    private lastGoldCheck: number | null = null;

    constructor(goldDiffToSpawnCoin = 10, millisecondsForGoldCount = 1000) {
        super();
        this.goldDiffToSpawnCoin = goldDiffToSpawnCoin;
        this.millisecondsForGoldCount = millisecondsForGoldCount;
    }

    override pollFrame(): Frame {
        this.addToFront(createGoldBar());
        this.spawnCoinIfNeeded();
        return super.pollFrame();
    }

    private spawnCoinIfNeeded(): void {
        const now = Date.now();
        if (now - this.getLastGoldCheck() >= this.millisecondsForGoldCount) {
            const currentGold = GameStateHelper.getGold();
            if (currentGold - this.getLastGold() >= this.goldDiffToSpawnCoin) {
                this.spawnCoin();
            }
            this.lastGold = currentGold;
            this.lastGoldCheck = now;
        }
    }

    spawnCoin(): void {
        const i = Math.trunc(Math.random() * 4);
        this.addToBack(fallingCoin(NUMPAD_COLUMNS[i]));
    }

    getLastGoldCheck(): number {
        if (this.lastGoldCheck === null) {
            this.lastGoldCheck = Date.now();
        }
        return this.lastGoldCheck;
    }

    getLastGold(): number {
        if (this.lastGold === null) {
            this.lastGold = GameStateHelper.getGold();
        }
        return this.lastGold;
    }
}

function createGoldBar(): ProgressBar {
    return new ProgressBar(GOLD_BAR_KEYS, GameStateHelper.getGoldPercentage(GOLD_FULL));
}

function fallingCoin(rzKeys: RzKey[]): AnimatedFrame {
    const animatedFrames = new AnimatedFrame();
    for (const rzKey of rzKeys) {
        animatedFrames.addAnimationFrame(new SimpleFrame(rzKey, StaticColor.YELLOW));
    }
    return animatedFrames;
}
