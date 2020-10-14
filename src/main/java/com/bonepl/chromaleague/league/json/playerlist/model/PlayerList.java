package com.bonepl.chromaleague.league.json.playerlist.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayerList {
    private final Player activePlayer;
    private final Map<String, Player> players;

    public PlayerList(String activePlayerName, Player[] players) {
        this.players = Arrays.stream(players)
                .collect(Collectors.toMap(Player::getSummonerName, Function.identity()));
        this.activePlayer = this.players.get(activePlayerName);
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public List<String> getAllies() {
        return players.values().stream()
                .filter(p -> Objects.equals(activePlayer.getTeam(), p.getTeam()))
                .map(Player::getSummonerName)
                .collect(Collectors.toList());
    }

    public List<String> getEnemies() {
        return players.values().stream()
                .filter(p -> !Objects.equals(activePlayer.getTeam(), p.getTeam()))
                .map(Player::getSummonerName)
                .collect(Collectors.toList());
    }

    public boolean isAlly(String summonersName) {
        return players.get(summonersName).getTeam() == activePlayer.getTeam();
    }
}
