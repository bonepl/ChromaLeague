package com.bonepl.chromaleague.razer.league.json.playerlist;

import com.bonepl.chromaleague.razer.league.json.GameDetectionThread;
import com.bonepl.chromaleague.razer.league.json.LeagueHttpClient;
import com.bonepl.chromaleague.razer.league.json.playerlist.model.Player;
import com.bonepl.chromaleague.razer.league.json.playerlist.model.PlayerList;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerListThread extends Thread {
    private final static Logger logger = LogManager.getLogger();

    private final LeagueHttpClient leagueHttpClient;
    boolean alive = true;
    PlayerList playerList;

    public PlayerListThread(LeagueHttpClient leagueHttpClient) {
        this.leagueHttpClient = leagueHttpClient;
    }

    public void run() {
        while (alive) {
            if (GameDetectionThread.isGameActive()) {
                if (playerList == null) {
                    playerList = new PlayerList(GameDetectionThread.getActivePlayerName(), fetchData());
                    logger.info("Player data loaded, summoner's team: " + playerList.getActivePlayer().getTeam().name());
                    logger.info("Player teammates: " + playerList.getAllies());
                    logger.info("Player enemies: " + playerList.getEnemies());
                }
            } else {
                playerList = null;
            }
        }
    }

    Player[] fetchData() {
        String json = leagueHttpClient.fetchJson("https://127.0.0.1:2999/liveclientdata/playerlist");
        if (json != null) {
            final Player[] jsonPlayers = JsonIterator.deserialize(json, Player[].class);
            if (jsonPlayers != null && jsonPlayers.length != 0) {
                return jsonPlayers;
            }
        }
        return null;
    }
}
