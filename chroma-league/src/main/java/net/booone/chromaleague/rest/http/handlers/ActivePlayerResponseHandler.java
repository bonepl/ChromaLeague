package net.booone.chromaleague.rest.http.handlers;

import com.jsoniter.JsonIterator;
import net.booone.chromaleague.rest.activeplayer.ActivePlayer;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.util.Optional;

public class ActivePlayerResponseHandler extends HttpOkBytesHandler
        implements ResponseHandler<Optional<ActivePlayer>> {

    @Override
    public Optional<ActivePlayer> handleResponse(HttpResponse response) throws IOException {
        return fetchBytesResponse(response)
                .map(activePlayer -> JsonIterator.deserialize(activePlayer, ActivePlayer.class));
    }
}
