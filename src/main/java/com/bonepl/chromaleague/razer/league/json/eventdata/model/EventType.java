package com.bonepl.chromaleague.razer.league.json.eventdata.model;

public enum EventType {
    UNSUPPORTED,
    BARON_KILL,
    HERALD_KILL,
    CLOUD_DRAGON_KILL,
    ELDER_DRAGON_KILL,
    INFERNAL_DRAGON_KILL,
    MOUNTAIN_DRAGON_KILL,
    OCEAN_DRAGON_KILL;

    public static EventType fromEvent(Event event) {
        if ("DragonKill".equals(event.getEventName())) {
            switch (event.getDragonType()) {
                case "Air": //confirmed
                    return CLOUD_DRAGON_KILL;
                case "Fire": //confirmed
                    return INFERNAL_DRAGON_KILL;
                case "Water":
                    return OCEAN_DRAGON_KILL;
                case "Earth": //confirmed
                    return MOUNTAIN_DRAGON_KILL;
                case "Elder":
                    return ELDER_DRAGON_KILL;
            }
        }

        if ("BaronKill".equals(event.getEventName())) {
            return BARON_KILL;
        }

        if ("HeraldKill".equals(event.getEventName())) {
            return HERALD_KILL;
        }

        return UNSUPPORTED;
    }
}
