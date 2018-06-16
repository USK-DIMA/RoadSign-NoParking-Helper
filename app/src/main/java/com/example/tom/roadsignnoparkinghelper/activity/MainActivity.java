package com.example.tom.roadsignnoparkinghelper.activity;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.example.tom.roadsignnoparkinghelper.R;
import com.example.tom.roadsignnoparkinghelper.models.SignFragmentModel;
import com.example.tom.roadsignnoparkinghelper.process.SignProcess;
import com.example.tom.roadsignnoparkinghelper.fragment.SignFragment;
import com.example.tom.roadsignnoparkinghelper.util.Supplier;

public class MainActivity extends AppCompatActivity {


    private FragmentManager fragmentManager = getSupportFragmentManager();
    private SignProcess process = new SignProcess();
    private SignFragment preferableSignFragment;
    private SignFragment signFragmentFirst;
    private SignFragment signFragmentSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(parent, name, context, attrs);
        preferableSignFragment = getSignFragment(R.id.preferableSignFragment, process.execute()::getPreferenceSignFragmentModel);
        signFragmentFirst = getSignFragment(R.id.signOneFragment, process.execute()::getSignFragmentModelFirst);
        signFragmentSecond = getSignFragment(R.id.signTwoFragment, process.execute()::getSignFragmentModelSecond);
        return view;
    }

    private SignFragment getSignFragment(int signFragmentId, final Supplier<SignFragmentModel> fragmentModelSupplier) {
        SignFragment fragment = (SignFragment) fragmentManager.findFragmentById(signFragmentId);
        if(fragment != null) {
            fragment.setFragmentCreatedListener(signFragment -> signFragment.fill(fragmentModelSupplier.get()));
        }
        return fragment;
    }


    @Override
    protected void onStart() {
        super.onStart();
        //updateFragments();
    }

/*    private void updateFragments() {
        System.out.println("Execute update");
        ActivityModel activityModel = process.execute();
        setSignType(preferableSignFragment, activityModel.getPreferenceSignFragmentModel());
        setSignType(signFragmentFirst, activityModel.getSignFragmentModelFirst());
        setSignType(signFragmentSecond, activityModel.getSignFragmentModelSecond());
    }

    private void setSignType(SignFragment signFragment, SignFragmentModel signFragmentModel) {
        if(signFragment != null && signFragment.isInited()) {
            signFragment.fill(signFragmentModel);
        }
    }*/

}
