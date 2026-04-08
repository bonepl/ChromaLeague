import {DragonType} from '../../rest/eventdata/DragonType.js';
import {AllyDragonKillBlinkingAnimation} from './AllyDragonKillBlinkingAnimation.js';

export class AllyHextechDragonKillAnimation extends AllyDragonKillBlinkingAnimation {
    constructor() {
        super(DragonType.HEXTECH);
    }
}
