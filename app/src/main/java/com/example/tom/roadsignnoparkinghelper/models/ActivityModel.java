package com.example.tom.roadsignnoparkinghelper.models;

public class ActivityModel {

    private SignFragmentModel signFragmentModelFirst;
    private SignFragmentModel signFragmentModelSecond;
    private SignFragmentModel preferenceSignFragmentModel;

    public ActivityModel(SignFragmentModel signFragmentModelFirst, SignFragmentModel signFragmentModelSecond) {
        this.signFragmentModelFirst = signFragmentModelFirst;
        this.signFragmentModelSecond = signFragmentModelSecond;
        if (signFragmentModelFirst.getAccessType() == signFragmentModelSecond.getAccessType()) {
            preferenceSignFragmentModel = signFragmentModelFirst.getDate().after(signFragmentModelSecond.getDate()) ? signFragmentModelFirst : signFragmentModelSecond;
        } else {
            preferenceSignFragmentModel = signFragmentModelFirst.getAccessType()== AccessType.ALLOWED ? signFragmentModelFirst : signFragmentModelSecond;
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

