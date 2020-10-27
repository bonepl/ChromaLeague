package com.bonepl.chromaleague.rest.eventdata;

import com.jsoniter.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Event {

    private final int eventID;
    private final String eventName;
    private final double eventTime;
    private final String dragonType;
    private final String killerName;
    private final String result;
    private final String victimName;
    private final List<String> assisters;

    @SuppressWarnings("ConstructorWithTooManyParameters")
    public Event(@JsonProperty("EventID") int eventID, @JsonProperty("EventName") String eventName, @JsonProperty("EventTime") double eventTime,
                 @JsonProperty("DragonType") String dragonType, @JsonProperty("KillerName") String killerName,
                 @JsonProperty("Result") String result, @JsonProperty("VictimName") String victimName, @JsonProperty("Assisters") List<String> assisters) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.dragonType = dragonType;
        this.killerName = killerName;
        this.result = result;
        this.victimName = victimName;
        this.assisters = new ArrayList<>(assisters);
    }

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

    @Override
    public String toString() {
        return "Event{" +
                "EventID=" + eventID +
                ", EventName='" + eventName + '\'' +
                ", EventTime=" + eventTime +
                ", DragonType='" + dragonType + '\'' +
                ", KillerName='" + killerName + '\'' +
                ", Result='" + result + '\'' +
                ", VictimName='" + victimName + '\'' +
                ", Assisters=" + assisters +
                '}';
    }
}
