package com.bonepl.chromaleague.rest.playerlist;

import com.bonepl.chromaleague.state.RunningState;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayerList {
    private final Map<String, Player> players;

    public PlayerList(Player... players) {
        this.players = Arrays.stream(players)
                .collect(Collectors.toMap(Player::summonerName, Function.identity()));
    }

    public Player getActivePlayer() {
        return players.get(RunningState.getGameState().getPlayerName());
    }

    public List<String> getAllies() {
        return players.values().stream()
                .filter(p -> getActivePlayer().team() == p.team())
                .map(Player::summonerName)
                .collect(Collectors.toList());
    }

    public List<String> getEnemies() {
        return players.values().stream()
                .filter(p -> getActivePlayer().team() != p.team())
                .map(Player::summonerName)
                .collect(Collectors.toList());
    }

    public boolean isAlly(String summonersName) {
        return players.get(summonersName).team() == getActivePlayer().team();
    }
}
