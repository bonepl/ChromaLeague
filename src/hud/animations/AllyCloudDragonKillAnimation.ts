import {DragonType} from '../../rest/eventdata/DragonType.js';
import {AllyDragonKillBlinkingAnimation} from './AllyDragonKillBlinkingAnimation.js';

export class AllyCloudDragonKillAnimation extends AllyDragonKillBlinkingAnimation {
    constructor() {
        super(DragonType.CLOUD);
    }
}
