import {DragonType} from '../../rest/eventdata/DragonType.js';
import {AllyDragonKillBlinkingAnimation} from './AllyDragonKillBlinkingAnimation.js';

export class AllyMountainDragonKillAnimation extends AllyDragonKillBlinkingAnimation {
    constructor() {
        super(DragonType.MOUNTAIN);
    }
}
