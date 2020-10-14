package com.bonepl.chromaleague.league.json.eventdata.model;

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
        if (event != null) {
            if ("DragonKill".equals(event.getEventName())) {
                switch (event.getDragonType()) {
                    case "Air":
                        return CLOUD_DRAGON_KILL;
                    case "Fire":
                        return INFERNAL_DRAGON_KILL;
                    case "Water":
                        return OCEAN_DRAGON_KILL;
                    case "Earth":
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
        }
        return UNSUPPORTED;
    }
}
