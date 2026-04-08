import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {Color} from '../../razer-sdk/color/Color.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {RzKey} from '../../razer-sdk/sdk/RzKey.js';
import {BLACKWIDOW_FUNCTIONAL} from '../PredefinedKeySets.js';

const DOT_COLOR = StaticColor.PURPLE;
const DIM_DOT = new StaticColor(80, 0, 80);

// Single-key path through the 3×3 box: up col 15, snake across to col 16, bounce, then down col 17
const DOT_PATH: RzKey[] = [
    RzKey.RZKEY_DELETE,      // row 2, col 15
    RzKey.RZKEY_INSERT,      // row 1, col 15
    RzKey.RZKEY_SCROLL,      // row 0, col 16
    RzKey.RZKEY_HOME,        // row 1, col 16
    RzKey.RZKEY_END,         // row 2, col 16
    RzKey.RZKEY_HOME,        // row 1, col 16  ← bounce back up
    RzKey.RZKEY_PAUSE,       // row 0, col 17
    RzKey.RZKEY_PAGEUP,      // row 1, col 17
    RzKey.RZKEY_PAGEDOWN,    // row 2, col 17
];

function buildBoxFrame(active: [RzKey, Color][]): Map<RzKey, Color> {
    const colorMap = new Map<RzKey, Color>();
    for (const key of BLACKWIDOW_FUNCTIONAL) colorMap.set(key, StaticColor.BLACK);
    for (const [key, color] of active) colorMap.set(key, color);
    return colorMap;
}

export class EnemyVoidGrubKillAnimation extends AnimatedFrame {
    constructor() {
        super();
        this.addOpeningBlinks();
        this.addDots();
        this.addClosingBlinks();
    }

    private addOpeningBlinks(): void {
        for (let i = 0; i < 3; i++) {
            this.addAnimationFrame(new SimpleFrame(BLACKWIDOW_FUNCTIONAL, DOT_COLOR));
            this.addAnimationFrame(2, new SimpleFrame(BLACKWIDOW_FUNCTIONAL, StaticColor.BLACK));
        }
    }

    private addDots(): void {
        const DOT_COUNT = 3;
        const STEP_FRAMES = 2;
        const FADE_FRAMES = 2;
        // Next dot starts when previous reaches END (step index 4 × 2 frames = 8)
        const START_OFFSET = 4 * STEP_FRAMES;
        const totalFrames = START_OFFSET * (DOT_COUNT - 1) + DOT_PATH.length * STEP_FRAMES + FADE_FRAMES;

        for (let t = 0; t < totalFrames; t++) {
            const active: [RzKey, Color][] = [];

            // Render trailing dots first so the leading dot wins on key conflicts
            for (let d = DOT_COUNT - 1; d >= 0; d--) {
                const localT = t - d * START_OFFSET;
                if (localT < 0) continue;

                const step = Math.floor(localT / STEP_FRAMES);
                if (step > DOT_PATH.length) continue; // dot fully finished

                if (step < DOT_PATH.length) {
                    if (step > 0) active.push([DOT_PATH[step - 1], DIM_DOT]);
                    active.push([DOT_PATH[step], DOT_COLOR]);
                } else {
                    // Fade: trail only on last key
                    active.push([DOT_PATH[DOT_PATH.length - 1], DIM_DOT]);
                }
            }

            this.addAnimationFrame(new SimpleFrame(buildBoxFrame(active)));
        }
    }

    private addClosingBlinks(): void {
        for (let i = 0; i < 3; i++) {
            this.addAnimationFrame(new SimpleFrame(BLACKWIDOW_FUNCTIONAL, DOT_COLOR));
            this.addAnimationFrame(2, new SimpleFrame(BLACKWIDOW_FUNCTIONAL, StaticColor.BLACK));
        }
    }
}
