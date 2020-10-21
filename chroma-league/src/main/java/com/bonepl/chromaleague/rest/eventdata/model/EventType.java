package com.bonepl.chromaleague.rest.eventdata.model;

import com.bonepl.chromaleague.GameState;
import com.bonepl.chromaleague.hud.DragonType;

import java.util.Objects;

import static com.bonepl.chromaleague.GameState.getPlayerList;

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
    GAME_END_VICTORY,
    GAME_END_DEFEAT,
    ACTIVE_PLAYER_DIED;

    public static EventType fromEvent(Event event) {
        if (event != null) {
            if ("ChampionKill".equals(event.getEventName()) && GameState.getActivePlayerName().equals(event.getVictimName())) {
                return ACTIVE_PLAYER_DIED;
            }

            if ("DragonKill".equals(event.getEventName())) {
                return switch (Objects.requireNonNull(DragonType.fromApiType(event.getDragonType()))) {
                    case CLOUD -> getPlayerList().isAlly(event.getKillerName()) ?
                            ALLY_CLOUD_DRAGON_KILL : ENEMY_CLOUD_DRAGON_KILL;
                    case INFERNAL -> getPlayerList().isAlly(event.getKillerName()) ?
                            ALLY_INFERNAL_DRAGON_KILL : ENEMY_INFERNAL_DRAGON_KILL;
                    case OCEAN -> getPlayerList().isAlly(event.getKillerName()) ?
                            ALLY_OCEAN_DRAGON_KILL : ENEMY_OCEAN_DRAGON_KILL;
                    case MOUNTAIN -> getPlayerList().isAlly(event.getKillerName()) ?
                            ALLY_MOUNTAIN_DRAGON_KILL : ENEMY_MOUNTAIN_DRAGON_KILL;
                    case ELDER -> getPlayerList().isAlly(event.getKillerName()) ?
                            ALLY_ELDER_DRAGON_KILL : ENEMY_ELDER_DRAGON_KILL;
                };
            }

            if ("BaronKill".equals(event.getEventName())) {
                return getPlayerList().isAlly(event.getKillerName()) ?
                        ALLY_BARON_KILL : ENEMY_BARON_KILL;
            }

            if ("HeraldKill".equals(event.getEventName())) {
                return getPlayerList().isAlly(event.getKillerName()) ?
                        ALLY_HERALD_KILL : ENEMY_HERALD_KILL;
            }

            if ("GameEnd".equals(event.getEventName())) {
                return "Win".equals(event.getResult()) ? GAME_END_VICTORY : GAME_END_DEFEAT;
            }
        }
        return UNSUPPORTED;
    }
}
