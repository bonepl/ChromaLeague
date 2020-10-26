package com.bonepl.razersdk.sdk.json.request;

import com.jsoniter.annotation.JsonProperty;

public class Init {
    String title;
    String description;
    InitAuthor author;
    @JsonProperty("device_supported")
    String[] deviceSupported;
    String category;

    public Init() {
        title = "Chroma League";
        description = "Razer Chroma SDK support for League of Legends";
        author = new InitAuthor("Jakub \"BonE\" Andrzejewski", "bone.pl@gmail.com");
        deviceSupported = new String[]{"keyboard"};
        category = "game";
    }
}
