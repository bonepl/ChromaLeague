package com.bonepl.chromaleague.rest;

import com.bonepl.chromaleague.rest.gamestats.GameStats;
import com.jsoniter.JsonIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.util.Optional;

public class GameStatsResponseHandler extends LiveClientResponseVerifier implements ResponseHandler<Optional<GameStats>> {

    @Override
    public Optional<GameStats> handleResponse(HttpResponse response) throws IOException {
        return fetchBytesResponse(response)
                .map(gameStats -> JsonIterator.deserialize(gameStats, GameStats.class));
    }
}
