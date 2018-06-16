package com.example.tom.roadsignnoparkinghelper.models;

import com.example.tom.roadsignnoparkinghelper.fragment.SignType;

import java.util.Date;

public class SignFragmentModel {

    private SignType signType;
    private boolean accessed;
    private Date date;

    public SignFragmentModel(SignType signType, boolean accessed, Date date) {
        this.signType = signType;
        this.accessed = accessed;
        this.date = date;
    }

    public SignType getSignType() {
        return signType;
    }

    public boolean isAccessed() {
        return accessed;
    }

    public Date getDate() {
        return date;
    }
}
