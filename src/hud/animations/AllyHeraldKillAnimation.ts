import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {Color} from '../../razer-sdk/color/Color.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {RzKey} from '../../razer-sdk/sdk/RzKey.js';
import {RzKeySelector} from '../../razer-sdk/sdk/RzKeySelector.js';
import {
    FIRST_NUMPAD_COLUMN,
    SECOND_NUMPAD_COLUMN,
    THIRD_NUMPAD_COLUMN,
    FOURTH_NUMPAD_COLUMN,
} from '../PredefinedKeySets.js';

const BALL_COLOR = new StaticColor(255, 0, 255);
const DIM_PURPLE = new StaticColor(100, 0, 100);
const VERY_DIM_PURPLE = new StaticColor(40, 0, 40);
const SPARK_COLOR = new StaticColor(255, 255, 255);

const TRAIL_COLORS: { offset: number; color: StaticColor }[] = [
    { offset: 3, color: new StaticColor(60, 60, 60) },
    { offset: 2, color: new StaticColor(130, 130, 130) },
    { offset: 1, color: new StaticColor(220, 220, 220) },
];
const NUMPAD_KEYS: RzKey[] = [
    ...FIRST_NUMPAD_COLUMN,
    ...SECOND_NUMPAD_COLUMN,
    ...THIRD_NUMPAD_COLUMN,
    ...FOURTH_NUMPAD_COLUMN,
];
// Right 1/3 of keyboard: cols 15-21, middle rows only (rows 1-4, like the ball)
const RIGHT_THIRD_KEYS: RzKey[] = new RzKeySelector()
    .withRowBetween(RzKey.RZKEY_TILDE, RzKey.RZKEY_LSHIFT)
    .withColumnBetween(RzKey.RZKEY_PRINTSCREEN, RzKey.RZKEY_NUMPAD_SUBTRACT)
    .asList();
// Right 1/3 split per row (rows 1-4) for the scatter effect
const RIGHT_THIRD_ROWS: RzKey[][] = Array.from({ length: 6 }, (_, i) =>
    new RzKeySelector()
        .withRow(i)
        .withColumnBetween(RzKey.RZKEY_PRINTSCREEN, RzKey.RZKEY_NUMPAD_SUBTRACT)
        .asList()
);
// Deterministic white spark keys for rows 1-4 (indexed by row number)
const SCATTER_SPARK_KEYS: Record<number, RzKey[]> = {
    1: [RzKey.RZKEY_PAGEUP, RzKey.RZKEY_NUMPAD_MULTIPLY],  // col 17, 20
    2: [RzKey.RZKEY_END, RzKey.RZKEY_NUMPAD9],             // col 16, 20
    3: [RzKey.RZKEY_NUMPAD5],                              // col 19 (only numpad in this row)
    4: [RzKey.RZKEY_UP, RzKey.RZKEY_NUMPAD3],             // col 16, 20
};

function buildScatterFrame(currentRow: number, withSparks: boolean): Map<RzKey, Color> {
    const colorMap = new Map<RzKey, Color>();
    for (let r = 1; r <= currentRow; r++) {
        const age = currentRow - r; // 0 = current row, 1 = one back, 2+ = older
        const color = age === 0 ? BALL_COLOR : age === 1 ? DIM_PURPLE : VERY_DIM_PURPLE;
        for (const key of RIGHT_THIRD_ROWS[r]) colorMap.set(key, color);
    }
    if (withSparks) {
        for (const key of SCATTER_SPARK_KEYS[currentRow]) colorMap.set(key, SPARK_COLOR);
    }
    return colorMap;
}

function getColumnKeys(col: number): RzKey[] {
    return new RzKeySelector().withColumn(col).withRowBetween(RzKey.RZKEY_TILDE, RzKey.RZKEY_LSHIFT).asList();
}

export class AllyHeraldKillAnimation extends AnimatedFrame {
    constructor() {
        super();
        this.addOpeningFlashes();
        this.addBallTraversal();
        this.addShatterEffect();
        this.addClosingFlashes();
    }

    private addOpeningFlashes(): void {
        for (let i = 0; i < 3; i++) {
            this.addAnimationFrame(new SimpleFrame(StaticColor.PURPLE));
            this.addAnimationFrame(2, new SimpleFrame(StaticColor.BLACK));
        }
    }

    private addBallTraversal(): void {
        for (let col = 0; col <= 21; col++) {
            const colorMap = new Map<RzKey, Color>();
            for (const { offset, color } of TRAIL_COLORS) {
                const trailCol = col - offset;
                if (trailCol >= 0) {
                    for (const key of getColumnKeys(trailCol)) {
                        colorMap.set(key, color);
                    }
                }
            }
            for (const key of getColumnKeys(col)) {
                colorMap.set(key, BALL_COLOR);
            }
            this.addAnimationFrame(new SimpleFrame(colorMap));
        }
    }

    private addShatterEffect(): void {
        // Impact: numpad burst, then full right 1/3 flash
        this.addAnimationFrame(2, new SimpleFrame(NUMPAD_KEYS, BALL_COLOR));
        this.addAnimationFrame(2, new SimpleFrame(RIGHT_THIRD_KEYS, BALL_COLOR));
        this.addAnimationFrame(new SimpleFrame(StaticColor.BLACK));
        // Scatter: cascade from top to bottom across middle rows only (1-4).
        // Two frames per row: sparks ON (white flicker), then sparks OFF.
        for (let row = 1; row <= 4; row++) {
            this.addAnimationFrame(new SimpleFrame(buildScatterFrame(row, true)));
            this.addAnimationFrame(new SimpleFrame(buildScatterFrame(row, false)));
        }
        // Fade out
        this.addAnimationFrame(new SimpleFrame(RIGHT_THIRD_KEYS, StaticColor.PURPLE));
        this.addAnimationFrame(2, new SimpleFrame(StaticColor.BLACK));
    }

    private addClosingFlashes(): void {
        for (let i = 0; i < 3; i++) {
            this.addAnimationFrame(new SimpleFrame(StaticColor.PURPLE));
            this.addAnimationFrame(2, new SimpleFrame(StaticColor.BLACK));
        }
    }
}
