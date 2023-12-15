package net.booone.chromaleague.rest.playerlist;

import net.booone.chromaleague.state.RunningState;

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
                .filter(player -> player.summonerName().equals(summonersName)
                        //RIOT ID migration bug mitigation
                        || (summonersName.contains("#") && player.summonerName().equals(summonersName.substring(0, summonersName.indexOf('#')))))
                .findAny().orElseThrow(() -> new IllegalStateException("Couldn't find player with name " + summonersName));
    }
}
