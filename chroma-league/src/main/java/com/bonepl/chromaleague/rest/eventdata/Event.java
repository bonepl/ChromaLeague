package com.bonepl.chromaleague.rest.eventdata;

import com.jsoniter.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Event {
    @JsonProperty("EventID")
    private int eventID;
    @JsonProperty("EventName")
    private String eventName;
    @JsonProperty("EventTime")
    private double eventTime;
    @JsonProperty("DragonType")
    private String dragonType;
    @JsonProperty("KillerName")
    private String killerName;
    @JsonProperty("Result")
    private String result;
    @JsonProperty("VictimName")
    private String victimName;
    @JsonProperty("Assisters")
    private List<String> assisters;

    public int getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public double getEventTime() {
        return eventTime;
    }

    public String getDragonType() {
        return dragonType;
    }

    public String getKillerName() {
        return killerName;
    }

    public String getResult() {
        return result;
    }

    public String getVictimName() {
        return victimName;
    }

    public List<String> getAssisters() {
        return Collections.unmodifiableList(assisters);
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        return eventID == event.eventID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventID);
    }
}
