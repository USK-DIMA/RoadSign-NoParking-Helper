package com.example.tom.roadsignnoparkinghelper.fragment;

import com.example.tom.roadsignnoparkinghelper.R;
import com.example.tom.roadsignnoparkinghelper.util.Function;

public enum SignType {

    FIRST(R.drawable.one, hour -> hour % 2 == 0),
    SECOND(R.drawable.two, hour -> hour % 2 != 0);

    private final int drawableId;
    private final Function<Integer, Boolean> parity;

    SignType(int drawableId, Function<Integer, Boolean> parity) {
        this.drawableId = drawableId;
        this.parity = parity;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public Function<Integer, Boolean> getParity() {
        return parity;
    }
}
