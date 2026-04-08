import {Color} from '../../razer-sdk/color/Color.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {AirColor} from '../../hud/colors/AirColor.js';
import {ChemtechColor} from '../../hud/colors/ChemtechColor.js';
import {CLColor} from '../../hud/colors/CLColor.js';
import {FireColor} from '../../hud/colors/FireColor.js';
import {HextechColor} from '../../hud/colors/HextechColor.js';
import {MountainColor} from '../../hud/colors/MountainColor.js';
import {OceanColor} from '../../hud/colors/OceanColor.js';

export enum DragonType {
    CHEMTECH = 'CHEMTECH',
    CLOUD = 'CLOUD',
    ELDER = 'ELDER',
    HEXTECH = 'HEXTECH',
    INFERNAL = 'INFERNAL',
    MOUNTAIN = 'MOUNTAIN',
    OCEAN = 'OCEAN',
}

const DRAGON_COLORS: Record<DragonType, StaticColor> = {
    [DragonType.CHEMTECH]: CLColor.CHEMTECH,
    [DragonType.CLOUD]: CLColor.AIR,
    [DragonType.ELDER]: StaticColor.WHITE,
    [DragonType.HEXTECH]: StaticColor.BLUE,
    [DragonType.INFERNAL]: StaticColor.RED,
    [DragonType.MOUNTAIN]: StaticColor.BROWN,
    [DragonType.OCEAN]: CLColor.OCEAN,
};

const DRAGON_SOUL_COLOR_FACTORIES: Record<DragonType, () => Color> = {
    [DragonType.CHEMTECH]: () => new ChemtechColor(),
    [DragonType.CLOUD]: () => new AirColor(),
    [DragonType.ELDER]: () => StaticColor.WHITE,
    [DragonType.HEXTECH]: () => new HextechColor(),
    [DragonType.INFERNAL]: () => new FireColor(),
    [DragonType.MOUNTAIN]: () => new MountainColor(),
    [DragonType.OCEAN]: () => new OceanColor(),
};

export function dragonTypeGetColor(dragonType: DragonType): StaticColor {
    return DRAGON_COLORS[dragonType];
}

export function dragonTypeGetSoulColor(dragonType: DragonType): Color {
    return DRAGON_SOUL_COLOR_FACTORIES[dragonType]();
}

const API_TYPE_MAP: Record<string, DragonType> = {
    Chemtech: DragonType.CHEMTECH,
    Air: DragonType.CLOUD,
    Elder: DragonType.ELDER,
    Hextech: DragonType.HEXTECH,
    Fire: DragonType.INFERNAL,
    Earth: DragonType.MOUNTAIN,
    Water: DragonType.OCEAN,
};

export function dragonTypeFromApiType(apiType: string): DragonType {
    const result = API_TYPE_MAP[apiType];
    if (result === undefined) {
        throw new Error(`Couldn't find dragon type for apiType ${apiType}`);
    }
    return result;
}
