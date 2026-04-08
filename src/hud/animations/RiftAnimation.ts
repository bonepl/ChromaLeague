import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {Color} from '../../razer-sdk/color/Color.js';
import {CircularRzKeySelector} from '../../razer-sdk/sdk/CircularRzKeySelector.js';
import {RzKey} from '../../razer-sdk/sdk/RzKey.js';
import {DragonType, dragonTypeGetColor} from '../../rest/eventdata/DragonType.js';
import {RunningState} from '../../state/RunningState.js';

function getColorForMapTerrain(): Color {
    const mapTerrain = RunningState.getGameState().gameStats!.mapTerrain;
    switch (mapTerrain) {
        case 'Mountain': return dragonTypeGetColor(DragonType.MOUNTAIN);
        case 'Ocean': return dragonTypeGetColor(DragonType.OCEAN);
        case 'Cloud': return dragonTypeGetColor(DragonType.CLOUD);
        case 'Infernal': return dragonTypeGetColor(DragonType.INFERNAL);
        case 'Hextech': return dragonTypeGetColor(DragonType.HEXTECH);
        case 'Chemtech': return dragonTypeGetColor(DragonType.CHEMTECH);
        default: throw new Error(`Something went wrong, no dragon type for mapTerrain: ${mapTerrain}`);
    }
}

export class RiftAnimation extends AnimatedFrame {
    constructor() {
        super();
        const circularRzKeySelector = new CircularRzKeySelector(RzKey.RZKEY_H, 2);
        const color = getColorForMapTerrain();

        let nextLayer = circularRzKeySelector.getNextLayer();
        while (nextLayer.size > 0) {
            this.addAnimationFrame(3, new SimpleFrame([...nextLayer], color));
            nextLayer = circularRzKeySelector.getNextLayer();
        }
    }
}
