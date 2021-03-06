package com.bonepl.chromaleague.rest.playerlist;

import com.bonepl.chromaleague.state.RunningState;

import java.util.List;
import java.util.stream.Collectors;

public record PlayerList(List<Player> players) {

    public Player getActivePlayer() {
        return getBySummonersName(RunningState.getGameState().getPlayerName());
    }

    public List<String> getAllies() {
        return players.stream()
                .filter(p -> getActivePlayer().team() == p.team())
                .map(Player::summonerName)
                .collect(Collectors.toList());
    }

    public List<String> getEnemies() {
        return players.stream()
                .filter(p -> getActivePlayer().team() != p.team())
                .map(Player::summonerName)
                .collect(Collectors.toList());
    }

    public boolean isAlly(String summonersName) {
        return getBySummonersName(summonersName).team() == getActivePlayer().team();
    }

    private Player getBySummonersName(String summonersName) {
        return players.stream()
                .filter(player -> player.summonerName().equals(summonersName))
                .findAny().orElseThrow(() -> new IllegalStateException("Couldn't find player with name " + summonersName));
    }
}
