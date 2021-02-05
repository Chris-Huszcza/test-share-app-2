package com.example.mytestapplication.schedulers;


import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class SchedulerTest {

    @Test
    public void testGetNextEventTime() {
        SchedulerConfig schedulerConfig = new SchedulerConfig();
        schedulerConfig.setInterval(Calendar.MINUTE, 15);
        // Mock Calendar so "now" is 08:10
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 8);
        calendar.set(Calendar.MINUTE, 10);
        Scheduler scheduler = new Scheduler(schedulerConfig);
        Calendar newTime = scheduler.getNextEventTime(calendar);

        // Assert next event is "now" + interval
        assertEquals(8, newTime.get(Calendar.HOUR));
        assertEquals(25, newTime.get(Calendar.MINUTE));
    }
}
