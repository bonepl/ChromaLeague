package com.bonepl.chromaleague.rest.eventdata.model;

public class Event {
    int EventID;
    String EventName;
    double EventTime;
    String DragonType;
    String KillerName;
    String Result;

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

    public String getKillerName() {
        return KillerName;
    }

    public String getResult() {
        return Result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "EventID=" + EventID +
                ", EventName='" + EventName + '\'' +
                ", EventTime=" + EventTime +
                ", DragonType='" + DragonType + '\'' +
                ", KillerName='" + KillerName + '\'' +
                ", Result='" + Result + '\'' +
                '}';
    }
}
