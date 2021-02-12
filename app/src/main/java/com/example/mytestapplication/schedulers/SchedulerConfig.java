package com.example.mytestapplication.schedulers;

import java.util.Calendar;

/**
 * Contains configuration for scheduling alarms
 */
public class SchedulerConfig {

    private Interval interval = new Interval(Calendar.MINUTE, 15);
    private final Time24Hour startTime = new Time24Hour(8, 0);
    private final Time24Hour endTime = new Time24Hour(16, 30);

    public SchedulerConfig() {

    }

    public void setInterval(int timeUnit, int amount) {
        this.interval = new Interval(timeUnit, amount);
    }

    public Interval getInterval() {
        return this.interval;
    }

    public Time24Hour getStartTime() {
        return this.startTime;
    }

    public Time24Hour getEndTime() {
        return this.endTime;
    }
}
