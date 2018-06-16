package com.example.tom.roadsignnoparkinghelper.process;

import com.example.tom.roadsignnoparkinghelper.models.AccessType;
import com.example.tom.roadsignnoparkinghelper.models.ActivityModel;
import com.example.tom.roadsignnoparkinghelper.models.DayType;

import junit.framework.Assert;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignProcessTest {

    private final SignProcess signProcess = new SignProcess();

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    
    private Calendar stringToCalendar(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DATE_FORMAT.parse(date));
        return cal;
    }

    @Test
    public void oddToEvenTest1() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("15.06.2018 17:00"));
        Assert.assertEquals(AccessType.DENIED, activityModel.getSignFragmentModelFirst().getAccessType());
        Assert.assertEquals(DayType.TODAY, activityModel.getSignFragmentModelFirst().getDateDayType());
        Assert.assertEquals("15.06.2018 19:00", DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertEquals(AccessType.ALLOWED, activityModel.getSignFragmentModelSecond().getAccessType());
        Assert.assertEquals(DayType.TODAY, activityModel.getSignFragmentModelSecond().getDateDayType());
        Assert.assertEquals("15.06.2018 21:00", DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));

        Assert.assertEquals(activityModel.getSignFragmentModelSecond(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void oddToEvenTest2() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("15.06.2018 20:00"));
        Assert.assertEquals(AccessType.ALLOWED, activityModel.getSignFragmentModelFirst().getAccessType());
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelFirst().getDateDayType());
        Assert.assertEquals("16.06.2018 21:00", DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertEquals(AccessType.ALLOWED, activityModel.getSignFragmentModelSecond().getAccessType());
        Assert.assertEquals(DayType.TODAY, activityModel.getSignFragmentModelSecond().getDateDayType());
        Assert.assertEquals("15.06.2018 21:00", DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelFirst(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void oddToEvenTest3() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("15.06.2018 21:00"));
        Assert.assertEquals(AccessType.ALLOWED, activityModel.getSignFragmentModelFirst().getAccessType());
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelFirst().getDateDayType());
        Assert.assertEquals("16.06.2018 21:00", DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertEquals(AccessType.DENIED, activityModel.getSignFragmentModelSecond().getAccessType());
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelSecond().getDateDayType());
        Assert.assertEquals("16.06.2018 19:00", DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelFirst(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void oddToEvenTest4() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("15.06.2018 22:00"));
        Assert.assertEquals(AccessType.ALLOWED, activityModel.getSignFragmentModelFirst().getAccessType());
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelFirst().getDateDayType());
        Assert.assertEquals("16.06.2018 21:00", DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertEquals(AccessType.DENIED, activityModel.getSignFragmentModelSecond().getAccessType());
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelSecond().getDateDayType());
        Assert.assertEquals("16.06.2018 19:00", DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelFirst(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void evenToOddTest1() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("16.06.2018 17:00"));
        Assert.assertEquals(AccessType.ALLOWED, activityModel.getSignFragmentModelFirst().getAccessType());
        Assert.assertEquals(DayType.TODAY, activityModel.getSignFragmentModelFirst().getDateDayType());
        Assert.assertEquals("16.06.2018 21:00", DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertEquals(AccessType.DENIED, activityModel.getSignFragmentModelSecond().getAccessType());
        Assert.assertEquals(DayType.TODAY, activityModel.getSignFragmentModelSecond().getDateDayType());
        Assert.assertEquals("16.06.2018 19:00", DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelFirst(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void evenToOddTest2() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("16.06.2018 20:00"));
        Assert.assertEquals(AccessType.ALLOWED, activityModel.getSignFragmentModelFirst().getAccessType());
        Assert.assertEquals(DayType.TODAY, activityModel.getSignFragmentModelFirst().getDateDayType());
        Assert.assertEquals("16.06.2018 21:00", DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertEquals(AccessType.ALLOWED, activityModel.getSignFragmentModelSecond().getAccessType());
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelSecond().getDateDayType());
        Assert.assertEquals("17.06.2018 21:00", DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelSecond(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void evenToOddTest3() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("16.06.2018 21:00"));
        Assert.assertEquals(AccessType.DENIED, activityModel.getSignFragmentModelFirst().getAccessType());
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelFirst().getDateDayType());
        Assert.assertEquals("17.06.2018 19:00", DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertEquals(AccessType.ALLOWED, activityModel.getSignFragmentModelSecond().getAccessType());
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelSecond().getDateDayType());
        Assert.assertEquals("17.06.2018 21:00", DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelSecond(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void evenToOddTest4() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("16.06.2018 22:00"));
        Assert.assertEquals(AccessType.DENIED, activityModel.getSignFragmentModelFirst().getAccessType());
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelFirst().getDateDayType());
        Assert.assertEquals("17.06.2018 19:00", DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertEquals(AccessType.ALLOWED, activityModel.getSignFragmentModelSecond().getAccessType());
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelSecond().getDateDayType());
        Assert.assertEquals("17.06.2018 21:00", DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelSecond(), activityModel.getPreferenceSignFragmentModel());
    }


    /**
     * ************************************
     * Переход с нечётного дня на нечётный
     * ************************************
     */


    @Test
    public void oddToOddTest1() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("31.01.2018 17:00"));
        Assert.assertEquals(AccessType.DENIED, activityModel.getSignFragmentModelFirst().getAccessType());
        Assert.assertEquals(DayType.TODAY, activityModel.getSignFragmentModelFirst().getDateDayType());
        Assert.assertEquals("31.01.2018 19:00", DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertEquals(AccessType.ALLOWED, activityModel.getSignFragmentModelSecond().getAccessType());
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelSecond().getDateDayType());
        Assert.assertEquals("01.02.2018 21:00", DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelSecond(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void oddToOddTest2() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("31.01.2018 19:00"));
        Assert.assertEquals(AccessType.ALLOWED, activityModel.getSignFragmentModelFirst().getAccessType());
        Assert.assertEquals(DayType.TODAY, activityModel.getSignFragmentModelFirst().getDateDayType());
        Assert.assertEquals("31.01.2018 21:00", DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertEquals(AccessType.ALLOWED, activityModel.getSignFragmentModelSecond().getAccessType());
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelSecond().getDateDayType());
        Assert.assertEquals("01.02.2018 21:00", DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelSecond(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void oddToOddTest3() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("31.01.2018 21:00"));
        Assert.assertEquals(AccessType.DENIED, activityModel.getSignFragmentModelFirst().getAccessType());
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelFirst().getDateDayType());
        Assert.assertEquals("01.02.2018 19:00", DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertEquals(AccessType.ALLOWED, activityModel.getSignFragmentModelSecond().getAccessType());
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelSecond().getDateDayType());
        Assert.assertEquals("01.02.2018 21:00", DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelSecond(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void oddToOddTest4() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("31.01.2018 22:00"));
        Assert.assertEquals(AccessType.DENIED, activityModel.getSignFragmentModelFirst().getAccessType());
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelFirst().getDateDayType());
        Assert.assertEquals("01.02.2018 19:00", DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertEquals(AccessType.ALLOWED, activityModel.getSignFragmentModelSecond().getAccessType());
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelSecond().getDateDayType());
        Assert.assertEquals("01.02.2018 21:00", DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelSecond(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void oddToOddTest5() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("30.01.2018 22:00"));
        Assert.assertEquals(AccessType.DENIED, activityModel.getSignFragmentModelFirst().getAccessType());
        
        Assert.assertEquals(DayType.TOMORROW, activityModel.getSignFragmentModelFirst().getDateDayType());
        Assert.assertEquals("31.01.2018 19:00", DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertEquals(AccessType.ALLOWED, activityModel.getSignFragmentModelSecond().getAccessType());
        Assert.assertEquals(DayType.DAY_AFTER_TOMORROW, activityModel.getSignFragmentModelSecond().getDateDayType());
        Assert.assertEquals("01.02.2018 21:00", DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelSecond(), activityModel.getPreferenceSignFragmentModel());
    }
}