import {DragonType} from '../../rest/eventdata/DragonType.js';
import {AllyDragonKillBlinkingAnimation} from './AllyDragonKillBlinkingAnimation.js';

export class AllyInfernalDragonKillAnimation extends AllyDragonKillBlinkingAnimation {
    constructor() {
        super(DragonType.INFERNAL);
    }
}
