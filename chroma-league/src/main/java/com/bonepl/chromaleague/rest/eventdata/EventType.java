package com.bonepl.chromaleague.rest.eventdata;

import com.bonepl.chromaleague.state.RunningState;

import java.util.Objects;

public enum EventType {
    UNSUPPORTED,
    ALLY_BARON_KILL,
    ALLY_HERALD_KILL,
    ALLY_CLOUD_DRAGON_KILL,
    ALLY_ELDER_DRAGON_KILL,
    ALLY_INFERNAL_DRAGON_KILL,
    ALLY_MOUNTAIN_DRAGON_KILL,
    ALLY_OCEAN_DRAGON_KILL,
    ENEMY_BARON_KILL,
    ENEMY_HERALD_KILL,
    ENEMY_CLOUD_DRAGON_KILL,
    ENEMY_ELDER_DRAGON_KILL,
    ENEMY_INFERNAL_DRAGON_KILL,
    ENEMY_MOUNTAIN_DRAGON_KILL,
    ENEMY_OCEAN_DRAGON_KILL,
    GAME_START,
    GAME_END_VICTORY,
    GAME_END_DEFEAT,
    ACTIVE_PLAYER_DIED,
    ACTIVE_PLAYER_KILL,
    ACTIVE_PLAYER_ASSIST;

    public static EventType fromEvent(Event event) {
        if (event != null) {
            if ("GameStart".equals(event.getEventName())) {
                return GAME_START;
            }
            if ("ChampionKill".equals(event.getEventName())) {
                final String activePlayerName = RunningState.getGameState().getActivePlayer().getSummonerName();
                if (activePlayerName.equals(event.getVictimName())) {
                    return ACTIVE_PLAYER_DIED;
                } else if (activePlayerName.equals(event.getKillerName())) {
                    return ACTIVE_PLAYER_KILL;
                } else if (event.getAssisters() != null && event.getAssisters().contains(activePlayerName)) {
                    return ACTIVE_PLAYER_ASSIST;
                }
            }

            if ("DragonKill".equals(event.getEventName())) {
                return switch (Objects.requireNonNull(DragonType.fromApiType(event.getDragonType()))) {
                    case CLOUD -> {
                        if (RunningState.getGameState().getPlayerList().isAlly(event.getKillerName())) {
                            yield ALLY_CLOUD_DRAGON_KILL;
                        }
                        yield ENEMY_CLOUD_DRAGON_KILL;
                    }
                    case INFERNAL -> {
                        if (RunningState.getGameState().getPlayerList().isAlly(event.getKillerName())) {
                            yield ALLY_INFERNAL_DRAGON_KILL;
                        }
                        yield ENEMY_INFERNAL_DRAGON_KILL;
                    }
                    case OCEAN -> {
                        if (RunningState.getGameState().getPlayerList().isAlly(event.getKillerName())) {
                            yield ALLY_OCEAN_DRAGON_KILL;
                        }
                        yield ENEMY_OCEAN_DRAGON_KILL;
                    }
                    case MOUNTAIN -> {
                        if (RunningState.getGameState().getPlayerList().isAlly(event.getKillerName())) {
                            yield ALLY_MOUNTAIN_DRAGON_KILL;
                        }
                        yield ENEMY_MOUNTAIN_DRAGON_KILL;
                    }
                    case ELDER -> {
                        if (RunningState.getGameState().getPlayerList().isAlly(event.getKillerName())) {
                            yield ALLY_ELDER_DRAGON_KILL;
                        }
                        yield ENEMY_ELDER_DRAGON_KILL;
                    }
                };
            }

            if ("BaronKill".equals(event.getEventName())) {
                if (RunningState.getGameState().getPlayerList().isAlly(event.getKillerName())) {
                    return ALLY_BARON_KILL;
                }
                return ENEMY_BARON_KILL;
            }

            if ("HeraldKill".equals(event.getEventName())) {
                if (RunningState.getGameState().getPlayerList().isAlly(event.getKillerName())) {
                    return ALLY_HERALD_KILL;
                }
                return ENEMY_HERALD_KILL;
            }

            if ("GameEnd".equals(event.getEventName())) {
                if ("Win".equals(event.getResult())) {
                    return GAME_END_VICTORY;
                }
                return GAME_END_DEFEAT;
            }
        }
        return UNSUPPORTED;
    }
}
