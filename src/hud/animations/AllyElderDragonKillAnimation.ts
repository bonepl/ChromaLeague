import {DragonType, dragonTypeGetColor} from '../../rest/eventdata/DragonType.js';
import {StaticBlinkingAnimation} from './StaticBlinkingAnimation.js';

export class AllyElderDragonKillAnimation extends StaticBlinkingAnimation {
    constructor() {
        super(dragonTypeGetColor(DragonType.ELDER));
    }
}
