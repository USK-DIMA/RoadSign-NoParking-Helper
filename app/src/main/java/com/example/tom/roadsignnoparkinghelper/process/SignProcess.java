package com.example.tom.roadsignnoparkinghelper.process;

import com.example.tom.roadsignnoparkinghelper.models.AccessType;
import com.example.tom.roadsignnoparkinghelper.models.DayType;
import com.example.tom.roadsignnoparkinghelper.models.SignType;
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

    private SignFragmentModel calculate(Calendar today, SignType signType) {
        AccessType accessType;
        DayType dayType;
        Date date;
        Function<Integer, Boolean> parity = signType.getParity();
        int currentDay = today.get(Calendar.DAY_OF_MONTH);
        int currentHour = today.get(Calendar.HOUR_OF_DAY);
        Calendar tomorrow = nextDay(today);
        int nextDay = tomorrow.get(Calendar.DAY_OF_MONTH);
        if ((parity.apply(currentDay) && (currentHour < CHANGE_OF_DAY_HOUR))
                || ((currentHour < CHANGE_OF_DAY_HOUR && currentHour >= FREE_HOUR))
                || (parity.apply(nextDay) && (currentHour >= CHANGE_OF_DAY_HOUR))
                ) {
            accessType = AccessType.ALLOWED;
            Calendar d = today;
            dayType = DayType.TODAY;
            if (parity.apply((nextDay))) {
                Calendar dayAfterTomorrow = nextDay(tomorrow);
                if(parity.apply(dayAfterTomorrow.get(Calendar.DAY_OF_MONTH))) {
                    d = dayAfterTomorrow;
                    dayType = DayType.DAY_AFTER_TOMORROW;
                } else {
                    d = tomorrow;
                    dayType = DayType.TOMORROW;
                }
            }
            date = createDate(d, CHANGE_OF_DAY_HOUR, 0);
        } else {
            accessType = AccessType.DENIED;
            if(currentHour < CHANGE_OF_DAY_HOUR) {
                dayType = DayType.TODAY;
                date = createDate(today, FREE_HOUR, 0);
            } else {
                dayType = DayType.TOMORROW;
                date = createDate(tomorrow, FREE_HOUR, 0);
            }
        }
        return new SignFragmentModel(signType, accessType, date, dayType);
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

    private Calendar nextDay(Calendar calendar) {
        Calendar clone = (Calendar) calendar.clone();
        clone.add(Calendar.DAY_OF_MONTH, 1);
        return clone;
    }
}
