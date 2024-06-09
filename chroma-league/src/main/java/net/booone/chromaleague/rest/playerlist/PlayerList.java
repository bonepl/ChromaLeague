package net.booone.chromaleague.rest.playerlist;

import net.booone.chromaleague.state.RunningState;

import java.util.List;
import java.util.stream.Collectors;

public record PlayerList(List<Player> players) {

    public Player getActivePlayer() {
        return getByRiotId(RunningState.getGameState().getPlayerRiotId());
    }

    public List<String> getAllies() {
        return players.stream()
                .filter(p -> getActivePlayer().team() == p.team())
                .map(Player::riotIdGameName)
                .collect(Collectors.toList());
    }

    public List<String> getEnemies() {
        return players.stream()
                .filter(p -> getActivePlayer().team() != p.team())
                .map(Player::riotIdGameName)
                .collect(Collectors.toList());
    }

    public boolean isAlly(String riotIdGameName) {
        return getByRiotIdGameName(riotIdGameName).team() == getActivePlayer().team();
    }

    private Player getByRiotId(String riotId) {
        return players.stream()
                .filter(player -> player.riotId().equals(riotId))
                .findAny().orElseThrow(() -> new IllegalStateException("Couldn't find player with riotId " + riotId));
    }

    private Player getByRiotIdGameName(String riotIdGameName) {
        return players.stream()
                .filter(player -> player.riotIdGameName().equals(riotIdGameName))
                .findAny().orElseThrow(() -> new IllegalStateException("Couldn't find player with riotIdGameName " + riotIdGameName));
    }
}
