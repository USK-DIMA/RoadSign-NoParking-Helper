package com.example.tom.roadsignnoparkinghelper.process;

import com.example.tom.roadsignnoparkinghelper.fragment.SignFragment;
import com.example.tom.roadsignnoparkinghelper.fragment.SignType;
import com.example.tom.roadsignnoparkinghelper.models.ActivityModel;
import com.example.tom.roadsignnoparkinghelper.models.SignFragmentModel;

import junit.framework.Assert;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static org.junit.Assert.*;

public class SignProcessTest {

    private final SignProcess signProcess = new SignProcess();

    private Calendar stringToCalendar(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(SignFragment.DATE_FORMAT.parse(date));// all done
        return cal;
    }

    @Test
    public void oddToEvenTest1() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("15.06.2018 17:00"));
        Assert.assertFalse(activityModel.getSignFragmentModelFirst().isAccessed());
        Assert.assertEquals("15.06.2018 19:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertTrue(activityModel.getSignFragmentModelSecond().isAccessed());
        Assert.assertEquals("15.06.2018 21:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelSecond(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void oddToEvenTest2() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("15.06.2018 20:00"));
        Assert.assertTrue(activityModel.getSignFragmentModelFirst().isAccessed());
        Assert.assertEquals("16.06.2018 21:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertTrue(activityModel.getSignFragmentModelSecond().isAccessed());
        Assert.assertEquals("15.06.2018 21:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelFirst(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void oddToEvenTest3() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("15.06.2018 21:00"));
        Assert.assertTrue(activityModel.getSignFragmentModelFirst().isAccessed());
        Assert.assertEquals("16.06.2018 21:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertFalse(activityModel.getSignFragmentModelSecond().isAccessed());
        Assert.assertEquals("16.06.2018 19:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelFirst(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void oddToEvenTest4() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("15.06.2018 22:00"));
        Assert.assertTrue(activityModel.getSignFragmentModelFirst().isAccessed());
        Assert.assertEquals("16.06.2018 21:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertFalse(activityModel.getSignFragmentModelSecond().isAccessed());
        Assert.assertEquals("16.06.2018 19:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelFirst(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void evenToOddTest1() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("16.06.2018 17:00"));
        Assert.assertTrue(activityModel.getSignFragmentModelFirst().isAccessed());
        Assert.assertEquals("16.06.2018 21:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertFalse(activityModel.getSignFragmentModelSecond().isAccessed());
        Assert.assertEquals("16.06.2018 19:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelFirst(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void evenToOddTest2() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("16.06.2018 20:00"));
        Assert.assertTrue(activityModel.getSignFragmentModelFirst().isAccessed());
        Assert.assertEquals("16.06.2018 21:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertTrue(activityModel.getSignFragmentModelSecond().isAccessed());
        Assert.assertEquals("17.06.2018 21:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelSecond(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void evenToOddTest3() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("16.06.2018 21:00"));
        Assert.assertFalse(activityModel.getSignFragmentModelFirst().isAccessed());
        Assert.assertEquals("17.06.2018 19:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertTrue(activityModel.getSignFragmentModelSecond().isAccessed());
        Assert.assertEquals("17.06.2018 21:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelSecond(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void evenToOddTest4() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("16.06.2018 22:00"));
        Assert.assertFalse(activityModel.getSignFragmentModelFirst().isAccessed());
        Assert.assertEquals("17.06.2018 19:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertTrue(activityModel.getSignFragmentModelSecond().isAccessed());
        Assert.assertEquals("17.06.2018 21:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
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
        Assert.assertFalse(activityModel.getSignFragmentModelFirst().isAccessed());
        Assert.assertEquals("31.01.2018 19:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertTrue(activityModel.getSignFragmentModelSecond().isAccessed());
        Assert.assertEquals("01.02.2018 21:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelSecond(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void oddToOddTest2() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("31.01.2018 19:00"));
        Assert.assertTrue(activityModel.getSignFragmentModelFirst().isAccessed());
        Assert.assertEquals("31.01.2018 21:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertTrue(activityModel.getSignFragmentModelSecond().isAccessed());
        Assert.assertEquals("01.02.2018 21:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelSecond(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void oddToOddTest3() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("31.01.2018 21:00"));
        Assert.assertFalse(activityModel.getSignFragmentModelFirst().isAccessed());
        Assert.assertEquals("01.02.2018 19:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertTrue(activityModel.getSignFragmentModelSecond().isAccessed());
        Assert.assertEquals("01.02.2018 21:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelSecond(), activityModel.getPreferenceSignFragmentModel());
    }

    @Test
    public void oddToOddTest4() throws ParseException {
        ActivityModel activityModel;
        activityModel = signProcess.execute(stringToCalendar("31.01.2018 22:00"));
        Assert.assertFalse(activityModel.getSignFragmentModelFirst().isAccessed());
        Assert.assertEquals("01.02.2018 19:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelFirst().getDate()));
        Assert.assertTrue(activityModel.getSignFragmentModelSecond().isAccessed());
        Assert.assertEquals("01.02.2018 21:00", SignFragment.DATE_FORMAT.format(activityModel.getSignFragmentModelSecond().getDate()));
        Assert.assertEquals(activityModel.getSignFragmentModelSecond(), activityModel.getPreferenceSignFragmentModel());
    }
}