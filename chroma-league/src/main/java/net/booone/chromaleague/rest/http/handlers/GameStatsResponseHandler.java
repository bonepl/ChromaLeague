package net.booone.chromaleague.rest.http.handlers;

import com.jsoniter.JsonIterator;
import net.booone.chromaleague.rest.gamestats.GameStats;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.util.Optional;

public class GameStatsResponseHandler extends HttpOkBytesHandler
        implements ResponseHandler<Optional<GameStats>> {

    @Override
    public Optional<GameStats> handleResponse(HttpResponse response) throws IOException {
        return fetchBytesResponse(response)
                .map(gameStats -> JsonIterator.deserialize(gameStats, GameStats.class));
    }
}
