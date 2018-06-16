package com.example.tom.roadsignnoparkinghelper.process;

import com.example.tom.roadsignnoparkinghelper.fragment.SignType;
import com.example.tom.roadsignnoparkinghelper.models.ActivityModel;
import com.example.tom.roadsignnoparkinghelper.models.SignFragmentModel;
import com.example.tom.roadsignnoparkinghelper.util.Function;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SignProcess {

    private static final int CHANGE_OF_DAY_HOUR = 21;
    private static final int FREE_HOUR = 19;

    public ActivityModel execute(Calendar calendar) {
        SignFragmentModel signFragmentModelFirst = calculate(calendar, SignType.FIRST);
        SignFragmentModel signFragmentModelSecond = calculate(calendar, SignType.SECOND);
        return new ActivityModel(signFragmentModelFirst, signFragmentModelSecond);
    }

    public ActivityModel execute() {
        return execute(GregorianCalendar.getInstance());
    }

    private SignFragmentModel calculate(Calendar currentDate, SignType signType) {
        boolean isAccessed;
        Date date;
        Function<Integer, Boolean> parity = signType.getParity();
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
        Calendar nextDate = (Calendar) currentDate.clone();
        nextDate.add(Calendar.DAY_OF_MONTH, 1);
        int nextDay = nextDate.get(Calendar.DAY_OF_MONTH);
        if ((parity.apply(currentDay) && (currentHour < CHANGE_OF_DAY_HOUR))
                || ((currentHour < CHANGE_OF_DAY_HOUR && currentHour >= FREE_HOUR))
                || (parity.apply(nextDay) && (currentHour >= CHANGE_OF_DAY_HOUR))
                ) {
            isAccessed = true;
            if (parity.apply((nextDay))) {
                date = createDate(nextDate, CHANGE_OF_DAY_HOUR, 0);
            } else {
                date = createDate(currentDate, CHANGE_OF_DAY_HOUR, 0);
            }
        } else {
            isAccessed = false;
            if(currentHour < CHANGE_OF_DAY_HOUR) {
                date = createDate(currentDate, FREE_HOUR, 0);
            } else {
                date = createDate(nextDate, FREE_HOUR, 0);
            }
        }
        return new SignFragmentModel(signType, isAccessed, date);
    }

    private Date createDate(Calendar calendarDay, int hour, int min) {
        return  new Date(calendarYearToDateYear(calendarDay.get(Calendar.YEAR)),
                calendarDay.get(Calendar.MONTH),
                calendarDay.get(Calendar.DAY_OF_MONTH),
                hour, min, 0
        );
    }

    private int calendarYearToDateYear(int calendarYear) {
        return calendarYear - 1900;
    }
}
