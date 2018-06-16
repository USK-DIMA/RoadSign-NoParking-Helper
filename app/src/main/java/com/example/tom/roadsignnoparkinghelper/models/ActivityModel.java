package com.example.tom.roadsignnoparkinghelper.models;

public class ActivityModel {

    private SignFragmentModel signFragmentModelFirst;
    private SignFragmentModel signFragmentModelSecond;
    private SignFragmentModel preferenceSignFragmentModel;

    public ActivityModel(SignFragmentModel signFragmentModelFirst, SignFragmentModel signFragmentModelSecond) {
        this.signFragmentModelFirst = signFragmentModelFirst;
        this.signFragmentModelSecond = signFragmentModelSecond;
        if (signFragmentModelFirst.isAccessed() == signFragmentModelSecond.isAccessed()) {
            preferenceSignFragmentModel = signFragmentModelFirst.getDate().after(signFragmentModelSecond.getDate()) ? signFragmentModelFirst : signFragmentModelSecond;
        } else {
            preferenceSignFragmentModel = signFragmentModelFirst.isAccessed() ? signFragmentModelFirst : signFragmentModelSecond;
        }
    }


    public SignFragmentModel getPreferenceSignFragmentModel() {
        return preferenceSignFragmentModel;
    }

    public SignFragmentModel getSignFragmentModelFirst() {
        return signFragmentModelFirst;
    }

    public SignFragmentModel getSignFragmentModelSecond() {
        return signFragmentModelSecond;
    }
}

