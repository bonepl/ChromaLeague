package net.booone.chromaleague.rest.http.handlers;

import com.jsoniter.JsonIterator;
import net.booone.chromaleague.rest.gamestats.GameStats;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

import java.io.IOException;
import java.util.Optional;

public class GameStatsResponseHandler extends HttpOkBytesHandler
        implements HttpClientResponseHandler<Optional<GameStats>> {

    @Override
    public Optional<GameStats> handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
        return fetchBytesResponse(response)
                .map(gameStats -> JsonIterator.deserialize(gameStats, GameStats.class));
    }
}
