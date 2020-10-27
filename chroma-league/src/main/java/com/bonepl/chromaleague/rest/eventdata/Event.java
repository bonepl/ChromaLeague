package com.bonepl.chromaleague.rest.eventdata;

import com.jsoniter.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

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
    private List<String> Assisters;

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
        return Collections.unmodifiableList(Assisters);
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
                ", Assisters=" + Assisters +
                '}';
    }
}
