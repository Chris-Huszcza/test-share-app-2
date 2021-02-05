package com.example.mytestapplication.schedulers;


import java.util.Calendar;

/**
 * Schedules alarms so they are only raised during set constraints (i.e. during LSE trading hours).
 */
public class Scheduler {

    private SchedulerConfig schedulerConfig;

    public Scheduler(SchedulerConfig alarmConfig) {
        this.schedulerConfig = alarmConfig;
    }

    public Calendar getNextEventTime(Calendar baseTime) {
        // Calculate now.
        //Calendar calendar = Calendar.getInstance();
        // Apply interval.
        baseTime.add(this.schedulerConfig.getInterval().getTimeUnit(),
                this.schedulerConfig.getInterval().getAmount());
        // If new time is outside time limits, set to startTime of next applicable day

        return baseTime;
    }
}
