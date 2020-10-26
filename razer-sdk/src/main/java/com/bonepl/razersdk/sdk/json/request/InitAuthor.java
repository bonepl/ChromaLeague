package com.bonepl.razersdk.sdk.json.request;

public class InitAuthor {
    private final String name;
    private final String contact;

    public InitAuthor(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }
}
