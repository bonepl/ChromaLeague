import {Color} from '../../razer-sdk/color/Color.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {DragonType, dragonTypeGetColor} from '../eventdata/DragonType.js';

export enum MapTerrain {
    CHEMTECH = 'CHEMTECH',
    CLOUD = 'CLOUD',
    HEXTECH = 'HEXTECH',
    INFERNAL = 'INFERNAL',
    MOUNTAIN = 'MOUNTAIN',
    OCEAN = 'OCEAN',
}

const API_TYPE_MAP: Record<string, MapTerrain> = {
    Chemtech: MapTerrain.CHEMTECH,
    Cloud: MapTerrain.CLOUD,
    Hextech: MapTerrain.HEXTECH,
    Infernal: MapTerrain.INFERNAL,
    Mountain: MapTerrain.MOUNTAIN,
    Ocean: MapTerrain.OCEAN,
};

const DRAGON_TYPE_MAP: Record<MapTerrain, DragonType> = {
    [MapTerrain.CHEMTECH]: DragonType.CHEMTECH,
    [MapTerrain.CLOUD]: DragonType.CLOUD,
    [MapTerrain.HEXTECH]: DragonType.HEXTECH,
    [MapTerrain.INFERNAL]: DragonType.INFERNAL,
    [MapTerrain.MOUNTAIN]: DragonType.MOUNTAIN,
    [MapTerrain.OCEAN]: DragonType.OCEAN,
};

export function mapTerrainFromApiType(apiType: string): MapTerrain {
    const result = API_TYPE_MAP[apiType];
    if (result === undefined) {
        throw new Error(`MapTerrain for apiType ${apiType} does not exist`);
    }
    return result;
}

export function mapTerrainDragonType(terrain: MapTerrain): DragonType {
    return DRAGON_TYPE_MAP[terrain];
}

export function mapTerrainGetBackgroundColor(terrain: MapTerrain): Color {
    const color = dragonTypeGetColor(mapTerrainDragonType(terrain));
    return new StaticColor(
        Math.trunc(0.06 * color.red),
        Math.trunc(0.06 * color.green),
        Math.trunc(0.06 * color.blue),
    );
}
