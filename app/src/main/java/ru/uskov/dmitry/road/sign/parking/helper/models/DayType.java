package ru.uskov.dmitry.road.sign.parking.helper.models;

import android.content.res.Resources;

import ru.uskov.dmitry.road.sign.parking.helper.R;

public enum DayType {
    TODAY(R.string.today),
    TOMORROW(R.string.tomorrow),
    DAY_AFTER_TOMORROW(R.string.day_after_tomorrow);

    private int resourceId;

    DayType(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getText(Resources resources) {
        return resources.getString(resourceId);
    }
}