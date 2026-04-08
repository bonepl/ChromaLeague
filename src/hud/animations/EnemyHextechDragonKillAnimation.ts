import {DragonType} from '../../rest/eventdata/DragonType.js';
import {EnemyDragonKillBlinkingAnimation} from './EnemyDragonKillBlinkingAnimation.js';

export class EnemyHextechDragonKillAnimation extends EnemyDragonKillBlinkingAnimation {
    constructor() {
        super(DragonType.HEXTECH);
    }
}
