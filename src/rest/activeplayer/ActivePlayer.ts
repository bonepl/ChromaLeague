import {ChampionStats} from './ChampionStats.js';

export interface ActivePlayer {
    riotId: string;
    riotIdGameName: string;
    currentGold: number;
    level: number;
    championStats: ChampionStats;
}
