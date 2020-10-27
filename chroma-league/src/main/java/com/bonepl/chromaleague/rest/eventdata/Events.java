package com.bonepl.chromaleague.rest.eventdata;

import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Events {

    private final List<Event> events;

    @JsonCreator
    public Events(@JsonProperty("Events") List<Event> events) {
        this.events = new ArrayList<>(events);
    }

    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }
}
