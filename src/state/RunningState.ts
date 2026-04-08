import {ChangeAwareBoolean} from '../tasks/ChangeAwareBoolean.js';
import {GameState} from './GameState.js';

export class RunningState {
    private static riotApi = new ChangeAwareBoolean();
    private static runningGame = new ChangeAwareBoolean();
    private static gameState = new GameState();
    static getRiotApi(): ChangeAwareBoolean {
        return this.riotApi;
    }

    static setRiotApi(value: boolean): void {
        this.riotApi.setValue(value);
        if (!value) {
            this.runningGame.reset();
        }
    }

    static getRunningGame(): ChangeAwareBoolean {
        return this.runningGame;
    }

    static setRunningGame(value: boolean): void {
        if (this.runningGame.different(value)) {
            this.gameState = new GameState();
        }
        this.runningGame.setValue(value);
    }

    static getGameState(): GameState {
        return this.gameState;
    }
}
