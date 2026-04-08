import {DragonType} from '../../rest/eventdata/DragonType.js';
import {BLACKWIDOW_FUNCTIONAL} from '../PredefinedKeySets.js';
import {AllyDragonKillBlinkingAnimation} from './AllyDragonKillBlinkingAnimation.js';

export class EnemyDragonKillBlinkingAnimation extends AllyDragonKillBlinkingAnimation {
    constructor(dragonType: DragonType) {
        super(BLACKWIDOW_FUNCTIONAL, dragonType);
    }
}
