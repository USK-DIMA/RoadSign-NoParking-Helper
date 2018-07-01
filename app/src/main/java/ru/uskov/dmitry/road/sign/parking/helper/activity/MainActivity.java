package ru.uskov.dmitry.road.sign.parking.helper.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import ru.uskov.dmitry.road.sign.parking.helper.R;
import ru.uskov.dmitry.road.sign.parking.helper.fragment.SignFragment;
import ru.uskov.dmitry.road.sign.parking.helper.models.ActivityModel;
import ru.uskov.dmitry.road.sign.parking.helper.models.SignFragmentModel;
import ru.uskov.dmitry.road.sign.parking.helper.process.SignProcess;
import ru.uskov.dmitry.road.sign.parking.helper.util.Consumer;
import ru.uskov.dmitry.road.sign.parking.helper.util.Supplier;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final SimpleDateFormat TIME_DATE_FORMAT = new SimpleDateFormat("HH:mm");

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private SignProcess process = new SignProcess();
    private SignFragment preferableSignFragment;
    private SignFragment signFragmentFirst;
    private SignFragment signFragmentSecond;
    private TextView currentTimeTextView;
    private TextView currentDateTextView;
    private final Timer timer = new Timer();
    private final TimerTask updateActivityTimerTask = new UpdateActivityTimerTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentTimeTextView = findViewById(R.id.currentTime);
        currentDateTextView = findViewById(R.id.currentDate);
        updateCurrentTime();
        scheduleActivityUpdate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void scheduleActivityUpdate() {
        Date currentDate = new Date();
        Date scheduleStartDate = new Date(currentDate.getYear(),
                currentDate.getMonth(),
                currentDate.getDay(),
                currentDate.getHours(),
                currentDate.getMinutes());
        timer.scheduleAtFixedRate(updateActivityTimerTask, scheduleStartDate, 1000 * 60);
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
        if (fragment != null) {
            fragment.setFragmentCreatedListener(signFragment -> updateSignFragment(signFragment, fragmentModelSupplier));
        }
        return fragment;
    }

    private void updateSignFragments(Consumer<Runnable> uiRunnable) {
        ActivityModel activityModel = process.execute();
        updateSignFragment(uiRunnable, preferableSignFragment, activityModel::getPreferenceSignFragmentModel);
        updateSignFragment(uiRunnable, signFragmentFirst, activityModel::getSignFragmentModelFirst);
        updateSignFragment(uiRunnable, signFragmentSecond, activityModel::getSignFragmentModelSecond);
    }

    private void updateSignFragment(SignFragment signFragment, Supplier<SignFragmentModel> fragmentModelSupplier) {
        updateSignFragment(Runnable::run, signFragment, fragmentModelSupplier);
    }

    private void updateSignFragment(Consumer<Runnable> uiRunnable, SignFragment signFragment, Supplier<SignFragmentModel> fragmentModelSupplier) {
        if (signFragment != null) {
            signFragment.fill(uiRunnable, fragmentModelSupplier.get());
        }
    }

    private void updateCurrentTime() {
        updateCurrentTime(Runnable::run);
    }

    @SuppressLint("SetTextI18n")
    private void updateCurrentTime(Consumer<Runnable> uiRunner) {
        if (currentTimeTextView != null && currentDateTextView != null) {
            Date currentDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            String currentTime = TIME_DATE_FORMAT.format(currentDate);
            String dayOfWeek = dayOfWeekToString(c.get(Calendar.DAY_OF_WEEK));
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            String month = monthToString(c.get(Calendar.MONTH));
                uiRunner.accept(() -> {
                    currentTimeTextView.setText(currentTime);
                    currentDateTextView.setText(dayOfWeek + ", " + dayOfMonth + " " + month);
                });
        }
    }

    private String monthToString(int i) {
        int resourceId = R.string.undefined;
        switch (i) {
            case 0:
                resourceId = R.string.month_0;
                break;
            case 1:
                resourceId = R.string.month_1;
                break;
            case 2:
                resourceId = R.string.month_2;
                break;
            case 3:
                resourceId = R.string.month_3;
                break;
            case 4:
                resourceId = R.string.month_4;
                break;
            case 5:
                resourceId = R.string.month_5;
                break;
            case 6:
                resourceId = R.string.month_6;
                break;
            case 7:
                resourceId = R.string.month_7;
                break;
            case 8:
                resourceId = R.string.month_8;
                break;
            case 9:
                resourceId = R.string.month_9;
                break;
            case 10:
                resourceId = R.string.month_10;
                break;
            case 11:
                resourceId = R.string.month_11;
                break;
        }
        return getResources().getString(resourceId);
    }

    private String dayOfWeekToString(int i) {
        int resourceId = R.string.undefined;
        switch (i) {
            case 2:
                resourceId = R.string.day_of_week_0;
                break;
            case 3:
                resourceId = R.string.day_of_week_1;
                break;
            case 4:
                resourceId = R.string.day_of_week_2;
                break;
            case 5:
                resourceId = R.string.day_of_week_3;
                break;
            case 6:
                resourceId = R.string.day_of_week_4;
                break;
            case 7:
                resourceId = R.string.day_of_week_5;
                break;
            case 1:
                resourceId = R.string.day_of_week_6;
                break;
        }
        return getResources().getString(resourceId);
    }

    private class UpdateActivityTimerTask extends TimerTask {

        @Override
        public void run() {
            updateCurrentTime(MainActivity.this::runOnUiThread);
            updateSignFragments(MainActivity.this::runOnUiThread);
        }
    }
}
