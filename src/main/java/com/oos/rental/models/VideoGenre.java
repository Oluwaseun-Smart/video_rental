package com.oos.rental.models;

public enum VideoGenre {

    Action("Action"),
    Drama("Drama"),
    Romance("Romance"),
    Comedy("Comedy"),
    Horror("Horror");

    private String genre;

    VideoGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() { return genre; }
}
