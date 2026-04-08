import {DragonType} from '../../rest/eventdata/DragonType.js';
import {EnemyDragonKillBlinkingAnimation} from './EnemyDragonKillBlinkingAnimation.js';

export class EnemyCloudDragonKillAnimation extends EnemyDragonKillBlinkingAnimation {
    constructor() {
        super(DragonType.CLOUD);
    }
}
