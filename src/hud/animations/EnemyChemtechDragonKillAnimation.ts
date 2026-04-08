import {DragonType} from '../../rest/eventdata/DragonType.js';
import {EnemyDragonKillBlinkingAnimation} from './EnemyDragonKillBlinkingAnimation.js';

export class EnemyChemtechDragonKillAnimation extends EnemyDragonKillBlinkingAnimation {
    constructor() {
        super(DragonType.CHEMTECH);
    }
}
