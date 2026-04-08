import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {LayeredFrame} from '../../razer-sdk/animation/LayeredFrame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {BreathingColor} from '../../razer-sdk/color/BreathingColor.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {TransitionColor} from '../../razer-sdk/color/TransitionColor.js';
import {RzKey} from '../../razer-sdk/sdk/RzKey.js';
import {RzKeySelector} from '../../razer-sdk/sdk/RzKeySelector.js';
import {CLColor} from '../colors/CLColor.js';
import {BackgroundBreathingColor} from '../colors/BackgroundBreathingColor.js';
import {getCurrentBackgroundColor} from '../parts/Background.js';
import {getHealthBarKeys} from '../parts/health/HealthBar.js';
import {getEnergyBarChampions, getResourceBarKeys} from '../parts/resource/ResourceBars.js';
import {DRAGON_FURY_COLOR} from '../parts/resource/ShyvanaDragonFuryBar.js';
import {WIND_SHIELD_COLOR} from '../parts/resource/YasuoWindBar.js';
import * as GameStateHelper from '../../state/GameStateHelper.js';
import {RunningState} from '../../state/RunningState.js';

const FOURTH_ROW: RzKey[] = new RzKeySelector().withRowOf(RzKey.RZKEY_Q).withColumnBetween(RzKey.RZKEY_Q, RzKey.RZKEY_U).asList();
const THIRD_ROW: RzKey[] = new RzKeySelector().withRowOf(RzKey.RZKEY_A).withColumnBetween(RzKey.RZKEY_A, RzKey.RZKEY_H).asList();
const SECOND_ROW: RzKey[] = new RzKeySelector().withRowOf(RzKey.RZKEY_Z).withColumnBetween(RzKey.RZKEY_Z, RzKey.RZKEY_B).asList();
const FIRST_ROW: RzKey[] = new RzKeySelector().withRowOf(RzKey.RZKEY_LALT).withColumnBetween(RzKey.RZKEY_LALT, RzKey.RZKEY_SPACE).asList();
const STEPS = 20;

function getResourceColor(): StaticColor {
    if (GameStateHelper.getResourcePercentage() === 0) {
        return new StaticColor(5, 5, 5); // DEFAULT_BACKGROUND_COLOR
    }
    return getPlayerResourceToTransitionColor();
}

function getPlayerResourceToTransitionColor(): StaticColor {
    const activePlayerChampionName = RunningState.getGameState().playerList!
        .getActivePlayer(RunningState.getGameState().playerRiotId!).championName;
    if (getEnergyBarChampions().has(activePlayerChampionName)) {
        return StaticColor.YELLOW;
    }
    if (activePlayerChampionName === 'Shyvana') {
        return DRAGON_FURY_COLOR;
    }
    if (activePlayerChampionName === 'Yasuo') {
        return WIND_SHIELD_COLOR;
    }
    return CLColor.MANA;
}

function createYellowAnimatedFrame(delay: number, keys: RzKey[]): AnimatedFrame {
    const animatedFrame = new AnimatedFrame();
    animatedFrame.addAnimationFrame(delay, new SimpleFrame());
    const yellowBreathingColor: BreathingColor = new BackgroundBreathingColor(StaticColor.YELLOW, STEPS, true);
    for (let i = 0; i < STEPS << 1; i++) {
        const layeredFrame = new LayeredFrame();
        layeredFrame.addFrame(new SimpleFrame([...keys], yellowBreathingColor.getColor()));
        if (i < STEPS) {
            layeredFrame.addFrame(new SimpleFrame([...getHealthBarKeys()], getCurrentBackgroundColor()));
            layeredFrame.addFrame(new SimpleFrame([...getResourceBarKeys()], getCurrentBackgroundColor()));
        }
        animatedFrame.addAnimationFrame(layeredFrame);
    }
    return animatedFrame;
}

function createButtonsGlowAnimatedFrame(delay: number, keys: readonly RzKey[], waitTill: number): AnimatedFrame {
    const keyArr = [...keys];
    const animatedFrame = new AnimatedFrame();
    animatedFrame.addAnimationFrame(delay, new SimpleFrame());
    const yellowBreathingColor: BreathingColor = new BackgroundBreathingColor(StaticColor.YELLOW, STEPS, true);
    for (let i = 0; i < STEPS; i++) {
        animatedFrame.addAnimationFrame(new SimpleFrame(keyArr, yellowBreathingColor.getColor()));
    }
    for (let i = delay + (STEPS << 1); i < waitTill - delay; i++) {
        animatedFrame.addAnimationFrame(new SimpleFrame(keyArr, StaticColor.YELLOW));
    }
    return animatedFrame;
}

function createButtonsTransitionAnimatedFrame(delay: number, keys: readonly RzKey[], toColor: StaticColor): AnimatedFrame {
    const keyArr = [...keys];
    const animatedFrame = new AnimatedFrame();
    animatedFrame.addAnimationFrame(delay, new SimpleFrame());
    const buttonTransitionColor = new TransitionColor(StaticColor.YELLOW, toColor, STEPS);
    for (let i = 0; i < STEPS; i++) {
        animatedFrame.addAnimationFrame(new SimpleFrame(keyArr, buttonTransitionColor.getColor()));
    }
    return animatedFrame;
}

export class RespawnAnimation extends AnimatedFrame {
    constructor() {
        super();
        const delayBetweenRows = 5;
        const animatedFrames: AnimatedFrame[] = [
            createYellowAnimatedFrame(0, FIRST_ROW),
            createYellowAnimatedFrame(delayBetweenRows << 1, SECOND_ROW),
            createYellowAnimatedFrame(delayBetweenRows * 3, THIRD_ROW),
            createYellowAnimatedFrame(delayBetweenRows << 2, FOURTH_ROW),
            createButtonsGlowAnimatedFrame(delayBetweenRows * 5, getResourceBarKeys(), delayBetweenRows * 7),
            createButtonsGlowAnimatedFrame(delayBetweenRows * 6, getHealthBarKeys(), delayBetweenRows * 7),
            createButtonsTransitionAnimatedFrame(delayBetweenRows * 7, getResourceBarKeys(), getResourceColor()),
            createButtonsTransitionAnimatedFrame(delayBetweenRows * 7, getHealthBarKeys(), StaticColor.GREEN),
        ];
        for (let i = 0; i < delayBetweenRows * 7 + (STEPS << 1); i++) {
            const layeredFrame = new LayeredFrame();
            for (const af of animatedFrames) {
                if (af.hasFrame()) {
                    layeredFrame.addFrame(af);
                }
            }
            this.addAnimationFrame(layeredFrame);
        }
    }
}
