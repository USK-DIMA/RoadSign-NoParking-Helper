package com.example.tom.roadsignnoparkinghelper.models;

//todo move literals to string resources
public enum DayType {
    TODAY(""),
    TOMORROW("завтра "),
    DAY_AFTER_TOMORROW("послезавтра ");

    private String text;

    DayType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}