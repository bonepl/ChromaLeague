package com.bonepl.chromaleague.rest.eventdata;

import com.jsoniter.annotation.JsonCreator;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public record Event(int EventID, String EventName, double EventTime,
                    String DragonType, String KillerName, String Result,
                    String VictimName, List<String> Assisters) {
    @JsonCreator
    public Event {
    }
}
