package net.booone.chromaleague.rest.http.handlers;

import com.jsoniter.JsonIterator;
import net.booone.chromaleague.rest.eventdata.Event;
import net.booone.chromaleague.rest.eventdata.Events;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class EventsResponseHandler extends HttpOkBytesHandler
        implements HttpClientResponseHandler<Optional<List<Event>>> {

    @Override
    public Optional<List<Event>> handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
        return fetchBytesResponse(response)
                .map(events -> JsonIterator.deserialize(events, Events.class))
                .map(Events::events)
                .filter(events -> !events.isEmpty());
    }
}
