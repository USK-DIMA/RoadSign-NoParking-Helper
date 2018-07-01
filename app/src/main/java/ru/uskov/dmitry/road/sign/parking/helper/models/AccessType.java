package ru.uskov.dmitry.road.sign.parking.helper.models;

import android.content.res.Resources;
import android.graphics.Color;

import ru.uskov.dmitry.road.sign.parking.helper.R;

public enum AccessType {
    ALLOWED(R.string.allowed_before, 0xFF009900),
    DENIED(R.string.forbidden_before, Color.RED);

    private int resourceId;
    private int color;

    AccessType(int resourceId, int color) {
        this.resourceId = resourceId;
        this.color = color;
    }

    public String getText(Resources resources) {
        return resources.getString(resourceId);
    }

    public int getColor() {
        return color;
    }
}