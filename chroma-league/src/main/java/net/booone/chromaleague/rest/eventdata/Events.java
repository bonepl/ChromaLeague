package net.booone.chromaleague.rest.eventdata;

import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public record Events(List<Event> events) {

    @JsonCreator
    public Events(@JsonProperty("Events") List<Event> events) {
        this.events = new ArrayList<>(events);
    }
}
