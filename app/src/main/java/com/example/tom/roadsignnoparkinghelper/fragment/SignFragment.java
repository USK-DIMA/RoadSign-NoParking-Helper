package com.example.tom.roadsignnoparkinghelper.fragment;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tom.roadsignnoparkinghelper.R;
import com.example.tom.roadsignnoparkinghelper.models.SignFragmentModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignFragment extends Fragment {

    @SuppressLint("SimpleDateFormat")
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");

    private Resources res;
    private ImageView sign;
    private TextView textView;
    private FragmentCreatedListener fragmentCreatedListener;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign, container, false);
        res = getResources();
        sign = rootView.findViewById(R.id.signImage);
        textView = rootView.findViewById(R.id.infoMessage);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if (fragmentCreatedListener != null) {
            fragmentCreatedListener.notify(this);
        }
    }

    public void fill(SignFragmentModel signFragmentModel) {
        sign.setImageDrawable(res.getDrawable(signFragmentModel.getSignType().getDrawableId()));
        String text  = buildText(signFragmentModel);
        int color = signFragmentModel.getAccessType().getColor();
        textView.setTextColor(color);
        textView.setText(text);
    }

    private String buildText(SignFragmentModel signFragmentModel) {
        return signFragmentModel.getAccessType().getText() +
                signFragmentModel.getDateDayType().getText() +
                dateToString(signFragmentModel.getDate());
    }

    public void setFragmentCreatedListener(FragmentCreatedListener fragmentCreatedListener) {
        this.fragmentCreatedListener = fragmentCreatedListener;
    }

    public interface FragmentCreatedListener {
        void notify(SignFragment signFragment);
    }

    private String dateToString(Date date) {
        return DATE_FORMAT.format(date);
    }

}
