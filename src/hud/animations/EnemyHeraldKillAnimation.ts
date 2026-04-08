import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {Color} from '../../razer-sdk/color/Color.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {RzKey} from '../../razer-sdk/sdk/RzKey.js';
import {BLACKWIDOW_FUNCTIONAL} from '../PredefinedKeySets.js';

const BALL_COLOR = new StaticColor(255, 0, 255);
const DIM_PURPLE = new StaticColor(100, 0, 100);
const VERY_DIM_PURPLE = new StaticColor(40, 0, 40);
const WHITE = new StaticColor(255, 255, 255);

// Bottom 2 rows of the 3x3 box, grouped by column (left to right)
const COL15_BOTTOM: RzKey[] = [RzKey.RZKEY_INSERT, RzKey.RZKEY_DELETE];
const COL16_BOTTOM: RzKey[] = [RzKey.RZKEY_HOME, RzKey.RZKEY_END];
const COL17_BOTTOM: RzKey[] = [RzKey.RZKEY_PAGEUP, RzKey.RZKEY_PAGEDOWN];

// Blow-up cascade steps: Pause → Home+PageUp → End+PageDown
const BLOWUP_STEPS: RzKey[][] = [
    [RzKey.RZKEY_PAUSE],
    [RzKey.RZKEY_HOME, RzKey.RZKEY_PAGEUP],
    [RzKey.RZKEY_END, RzKey.RZKEY_PAGEDOWN],
];

function buildBoxFrame(activeKeys: [RzKey[], Color][]): Map<RzKey, Color> {
    const colorMap = new Map<RzKey, Color>();
    for (const key of BLACKWIDOW_FUNCTIONAL) colorMap.set(key, StaticColor.BLACK);
    for (const [keys, color] of activeKeys) {
        for (const key of keys) colorMap.set(key, color);
    }
    return colorMap;
}

export class EnemyHeraldKillAnimation extends AnimatedFrame {
    constructor() {
        super();
        this.addOpeningBlinks();
        this.addRunningLine();
        this.addBlowUp();
        this.addClosingBlinks();
    }

    private addOpeningBlinks(): void {
        for (let i = 0; i < 3; i++) {
            this.addAnimationFrame(new SimpleFrame(BLACKWIDOW_FUNCTIONAL, BALL_COLOR));
            this.addAnimationFrame(2, new SimpleFrame(BLACKWIDOW_FUNCTIONAL, StaticColor.BLACK));
        }
    }

    private addRunningLine(): void {
        const trail: [RzKey[], Color][][] = [
            [[COL15_BOTTOM, BALL_COLOR]],
            [[COL15_BOTTOM, DIM_PURPLE], [COL16_BOTTOM, BALL_COLOR]],
            [[COL15_BOTTOM, VERY_DIM_PURPLE], [COL16_BOTTOM, DIM_PURPLE], [COL17_BOTTOM, BALL_COLOR]],
            [[COL16_BOTTOM, VERY_DIM_PURPLE], [COL17_BOTTOM, DIM_PURPLE]],
            [[COL17_BOTTOM, VERY_DIM_PURPLE]],
        ];
        for (const frame of trail) {
            this.addAnimationFrame(3, new SimpleFrame(buildBoxFrame(frame)));
        }
    }

    private addBlowUp(): void {
        // Each step flashes white on arrival, then fades purple → dim → very dim
        const trail: [RzKey[], Color][][] = [
            [[BLOWUP_STEPS[0], WHITE]],
            [[BLOWUP_STEPS[0], BALL_COLOR], [BLOWUP_STEPS[1], WHITE]],
            [[BLOWUP_STEPS[0], DIM_PURPLE], [BLOWUP_STEPS[1], BALL_COLOR], [BLOWUP_STEPS[2], WHITE]],
            [[BLOWUP_STEPS[0], VERY_DIM_PURPLE], [BLOWUP_STEPS[1], DIM_PURPLE], [BLOWUP_STEPS[2], BALL_COLOR]],
            [[BLOWUP_STEPS[1], VERY_DIM_PURPLE], [BLOWUP_STEPS[2], DIM_PURPLE]],
            [[BLOWUP_STEPS[2], VERY_DIM_PURPLE]],
        ];
        for (const frame of trail) {
            this.addAnimationFrame(3, new SimpleFrame(buildBoxFrame(frame)));
        }
    }

    private addClosingBlinks(): void {
        for (let i = 0; i < 3; i++) {
            this.addAnimationFrame(new SimpleFrame(BLACKWIDOW_FUNCTIONAL, BALL_COLOR));
            this.addAnimationFrame(2, new SimpleFrame(BLACKWIDOW_FUNCTIONAL, StaticColor.BLACK));
        }
    }
}
