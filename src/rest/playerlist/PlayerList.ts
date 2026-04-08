import {Player} from './Player.js';

export class PlayerList {
    readonly players: Player[];

    constructor(players: Player[]) {
        this.players = players;
    }

    getActivePlayer(playerRiotId: string): Player {
        return this.getByRiotId(playerRiotId);
    }

    getAllies(playerRiotId: string): string[] {
        const active = this.getActivePlayer(playerRiotId);
        return this.players
            .filter(p => active.team === p.team)
            .map(p => p.riotIdGameName);
    }

    getEnemies(playerRiotId: string): string[] {
        const active = this.getActivePlayer(playerRiotId);
        return this.players
            .filter(p => active.team !== p.team)
            .map(p => p.riotIdGameName);
    }

    isAlly(killerName: string, playerRiotId: string): boolean {
        const player = this.getByRiotIdGameName(killerName)
            // Probable bug in Riot API - sometimes returns ChampionName as KillerName
            ?? this.getByChampionName(killerName);
        if (!player) {
            throw new Error(`Couldn't find player with riotIdGameName ${killerName}`);
        }
        return player.team === this.getActivePlayer(playerRiotId).team;
    }

    private getByRiotId(riotId: string): Player {
        const player = this.players.find(p => p.riotId === riotId);
        if (!player) {
            throw new Error(`Couldn't find player with riotId ${riotId}`);
        }
        return player;
    }

    private getByRiotIdGameName(riotIdGameName: string): Player | undefined {
        return this.players.find(p => p.riotIdGameName === riotIdGameName);
    }

    private getByChampionName(championName: string): Player | undefined {
        return this.players.find(p => p.championName === championName);
    }
}
