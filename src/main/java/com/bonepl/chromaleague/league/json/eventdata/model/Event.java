package com.bonepl.chromaleague.league.json.eventdata.model;

public class Event {
    int EventID;
    String EventName;
    double EventTime;
    String DragonType;

    public int getEventID() {
        return EventID;
    }

    public String getEventName() {
        return EventName;
    }

    public double getEventTime() {
        return EventTime;
    }

    public String getDragonType() {
        return DragonType;
    }

    @Override
    public String toString() {
        return "Event{" +
                "EventID=" + EventID +
                ", EventName='" + EventName + '\'' +
                ", EventTime=" + EventTime +
                ", DragonType='" + DragonType + '\'' +
                '}';
    }
}
