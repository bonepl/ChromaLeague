import {DragonType} from '../../rest/eventdata/DragonType.js';
import {AllyDragonKillBlinkingAnimation} from './AllyDragonKillBlinkingAnimation.js';

export class AllyChemtechDragonKillAnimation extends AllyDragonKillBlinkingAnimation {
    constructor() {
        super(DragonType.CHEMTECH);
    }
}
