package com.bonepl.razersdk.sdk.json.request;

import com.jsoniter.annotation.JsonProperty;

public class Init {
    private final String title;
    private final String description;
    private final InitAuthor author;
    private final String[] deviceSupported;
    private final String category;

    public Init() {
        title = "Chroma League";
        description = "Razer Chroma SDK support for League of Legends";
        author = new InitAuthor("Jakub \"BonE\" Andrzejewski", "bone.pl@gmail.com");
        deviceSupported = new String[]{"keyboard"};
        category = "game";
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public InitAuthor getAuthor() {
        return author;
    }

    @JsonProperty("device_supported")
    public String[] getDeviceSupported() {
        return deviceSupported.clone();
    }

    public String getCategory() {
        return category;
    }
}
