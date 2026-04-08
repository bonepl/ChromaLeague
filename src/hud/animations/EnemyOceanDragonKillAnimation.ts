import {DragonType} from '../../rest/eventdata/DragonType.js';
import {EnemyDragonKillBlinkingAnimation} from './EnemyDragonKillBlinkingAnimation.js';

export class EnemyOceanDragonKillAnimation extends EnemyDragonKillBlinkingAnimation {
    constructor() {
        super(DragonType.OCEAN);
    }
}
