package com.oos.rental.models;

public enum VideoType {

    Regular("Regular"),
    Children_Movie("Children’s Movie"),
    New_Release("New Release");

    private String type;

    VideoType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
