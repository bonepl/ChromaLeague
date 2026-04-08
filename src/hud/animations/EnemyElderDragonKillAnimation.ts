import {DragonType, dragonTypeGetColor} from '../../rest/eventdata/DragonType.js';
import {StaticPartialBlinkingAnimation} from './StaticPartialBlinkingAnimation.js';

export class EnemyElderDragonKillAnimation extends StaticPartialBlinkingAnimation {
    constructor() {
        super(dragonTypeGetColor(DragonType.ELDER));
    }
}
