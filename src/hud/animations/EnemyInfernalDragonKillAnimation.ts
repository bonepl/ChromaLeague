import {DragonType} from '../../rest/eventdata/DragonType.js';
import {EnemyDragonKillBlinkingAnimation} from './EnemyDragonKillBlinkingAnimation.js';

export class EnemyInfernalDragonKillAnimation extends EnemyDragonKillBlinkingAnimation {
    constructor() {
        super(DragonType.INFERNAL);
    }
}
