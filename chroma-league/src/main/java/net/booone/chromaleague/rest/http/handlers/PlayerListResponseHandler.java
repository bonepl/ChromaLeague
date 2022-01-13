package net.booone.chromaleague.rest.http.handlers;

import com.jsoniter.JsonIterator;
import net.booone.chromaleague.rest.playerlist.Player;
import net.booone.chromaleague.rest.playerlist.PlayerList;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class PlayerListResponseHandler extends HttpOkBytesHandler
        implements ResponseHandler<Optional<PlayerList>> {

    @Override
    public Optional<PlayerList> handleResponse(HttpResponse response) throws IOException {
        return fetchBytesResponse(response)
                .map(playerList -> JsonIterator.deserialize(playerList, Player[].class))
                .map(Arrays::asList)
                .map(PlayerList::new);
    }
}
