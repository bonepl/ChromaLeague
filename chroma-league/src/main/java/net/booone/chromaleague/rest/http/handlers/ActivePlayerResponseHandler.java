package net.booone.chromaleague.rest.http.handlers;

import com.jsoniter.JsonIterator;
import net.booone.chromaleague.rest.activeplayer.ActivePlayer;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

import java.io.IOException;
import java.util.Optional;

public class ActivePlayerResponseHandler extends HttpOkBytesHandler
        implements HttpClientResponseHandler<Optional<ActivePlayer>> {

    @Override
    public Optional<ActivePlayer> handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
        return fetchBytesResponse(response)
                .map(activePlayer -> JsonIterator.deserialize(activePlayer, ActivePlayer.class));
    }
}
