package com.example.tom.roadsignnoparkinghelper.models;

import android.graphics.Color;

//todo move literals to string resources
public enum AccessType {
    ALLOWED("Разрешено до ", 0xFF009900),
    DENIED("Запрещено до ", Color.RED);

    private String text;
    private int color;

    AccessType(String text, int color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public int getColor() {
        return color;
    }
}