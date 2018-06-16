package com.example.tom.roadsignnoparkinghelper.models;

import java.util.Date;

public class SignFragmentModel {

    private SignType signType;
    private AccessType accessType;
    private Date date;
    private DayType dateDayType;

    public SignFragmentModel(SignType signType, AccessType accessType, Date date, DayType dateDayType) {
        this.signType = signType;
        this.accessType = accessType;
        this.date = date;
        this.dateDayType = dateDayType;
    }

    public DayType getDateDayType() {
        return dateDayType;
    }

    public SignType getSignType() {
        return signType;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public Date getDate() {
        return date;
    }
}
