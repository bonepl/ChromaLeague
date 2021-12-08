package com.bonepl.chromaleague.rest;

import com.bonepl.chromaleague.rest.eventdata.Event;
import com.bonepl.chromaleague.rest.eventdata.Events;
import com.jsoniter.JsonIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class EventsResponseHandler extends LiveClientResponseVerifier implements ResponseHandler<Optional<List<Event>>> {

    @Override
    public Optional<List<Event>> handleResponse(HttpResponse response) throws IOException {
        return fetchBytesResponse(response)
                .map(events -> JsonIterator.deserialize(events, Events.class))
                .map(Events::events)
                .filter(events -> !events.isEmpty());
    }
}
