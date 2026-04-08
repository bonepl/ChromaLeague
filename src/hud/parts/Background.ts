import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {LayeredFrame} from '../../razer-sdk/animation/LayeredFrame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {BreathingColor} from '../../razer-sdk/color/BreathingColor.js';
import {Color} from '../../razer-sdk/color/Color.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {mapTerrainFromApiType, mapTerrainGetBackgroundColor} from '../../rest/gamestats/MapTerrain.js';
import * as GameStateHelper from '../../state/GameStateHelper.js';
import {RunningState} from '../../state/RunningState.js';
import {BaronBuffBackgroundAnimation} from '../animations/BaronBuffBackgroundAnimation.js';
import {BackgroundBreathingColor} from '../colors/BackgroundBreathingColor.js';
import {DEFAULT_BACKGROUND_COLOR} from './BackgroundConstants.js';

export { DEFAULT_BACKGROUND_COLOR };

const BARON_BUFF_BACKGROUND_ANIMATION: AnimatedFrame = new BaronBuffBackgroundAnimation();
const DEAD_BACKGROUND: BreathingColor = new BackgroundBreathingColor(new StaticColor(60, 40, 40));

export class Background extends LayeredFrame {
    constructor() {
        super();
        this.addFrame(new SimpleFrame(getCurrentBackgroundColor()));
        if (GameStateHelper.hasBaronBuff()) {
            this.addFrame(BARON_BUFF_BACKGROUND_ANIMATION);
        }
    }
}

export function getCurrentBackgroundColor(): Color {
    if (GameStateHelper.isActivePlayerAlive()) {
        if (RunningState.getGameState().eventData.riftAnimationPlayed) {
            return mapTerrainGetBackgroundColor(
                mapTerrainFromApiType(RunningState.getGameState().gameStats!.mapTerrain),
            );
        } else {
            return DEFAULT_BACKGROUND_COLOR;
        }
    }
    return DEAD_BACKGROUND.getColor();
}
