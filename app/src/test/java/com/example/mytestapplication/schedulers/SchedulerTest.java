package com.example.mytestapplication.schedulers;


import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class SchedulerTest {

    private SchedulerConfig schedulerConfig;
    private Scheduler scheduler;

    @Before
    public void setUp() {
        schedulerConfig = new SchedulerConfig();
        schedulerConfig.setInterval(Calendar.MINUTE, 15);
        scheduler = new Scheduler(schedulerConfig);
    }

    @Test
    public void testGetNextEventTime() {
        // Set Calendar so "now" is 08:10, Monday (Monday=2)
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 10);

        Calendar newTime = scheduler.getNextEventTime(calendar);

        // Assert next event is 08:15
        assertEquals(8, newTime.get(Calendar.HOUR_OF_DAY));
        assertEquals(25, newTime.get(Calendar.MINUTE));
    }

    @Test
    public void testGetEventNextDay() {
        // Set calendar so "now" is 16:31 on a Monday
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 31);

        Calendar newTime = scheduler.getNextEventTime(calendar);

        // Assert next event is 08:00, Tuesday
        assertEquals(Calendar.TUESDAY, newTime.get(Calendar.DAY_OF_WEEK));
        assertEquals(8, newTime.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, newTime.get(Calendar.MINUTE));
    }

    @Test
    public void testGetEventNextWeekday() {
        // Set calendar so "now" is 16:31 on a Friday
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 31);

        Calendar newTime = scheduler.getNextEventTime(calendar);

        // Assert next event is 08:00, Monday
        assertEquals(Calendar.MONDAY, newTime.get(Calendar.DAY_OF_WEEK));
        assertEquals(8, newTime.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, newTime.get(Calendar.MINUTE));
    }

    @Test
    public void testGetEventInMorning() {
        // Set calendar so "now" is 05:00 on a Friday
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 0);

        Calendar newTime = scheduler.getNextEventTime(calendar);

        // Assert next event is 08:00, Friday
        assertEquals(Calendar.FRIDAY, newTime.get(Calendar.DAY_OF_WEEK));
        assertEquals(8, newTime.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, newTime.get(Calendar.MINUTE));
    }
}
