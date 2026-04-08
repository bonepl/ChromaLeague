import {Team} from './Team.js';

export interface Player {
    riotId: string;
    riotIdGameName: string;
    team: Team;
    championName: string;
    respawnTimer: number;
    isDead: boolean;
}
