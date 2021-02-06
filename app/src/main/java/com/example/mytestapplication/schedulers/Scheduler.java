package com.example.mytestapplication.schedulers;

import java.util.Calendar;

/**
 * Schedules alarms so they are only raised during set constraints (i.e. during LSE trading hours).
 */
public class Scheduler {

    private final SchedulerConfig schedulerConfig;
    private Calendar time;

    public Scheduler() {
        this.schedulerConfig = new SchedulerConfig();
    }

    public Scheduler(SchedulerConfig alarmConfig) {
        this.schedulerConfig = alarmConfig;
    }

    /**
     *
     * @param baseTime Represents the "now" to work with
     * @return
     */
    public Calendar getNextEventTime(Calendar baseTime) {
        this.time = baseTime;
        Calendar newTime = (Calendar) baseTime.clone();
        // Apply interval.
        newTime.add(this.schedulerConfig.getInterval().getTimeUnit(),
                this.schedulerConfig.getInterval().getAmount());
        // If new time is outside time limits, set to startTime of next applicable day
        if (isBeforeStartTime(newTime)) {
            newTime.set(Calendar.HOUR_OF_DAY, schedulerConfig.getStartTime().getHour());
            newTime.set(Calendar.MINUTE, schedulerConfig.getStartTime().getMinute());
        }
        if (isAfterEndTime(newTime)) {
            setNextValidDay(newTime);
            newTime.set(Calendar.HOUR_OF_DAY, schedulerConfig.getStartTime().getHour());
            newTime.set(Calendar.MINUTE, schedulerConfig.getStartTime().getMinute());
        }
        return newTime;
    }

    private boolean isBeforeStartTime(Calendar timeToCheck) {
        Calendar startTimeToday = this.time;
        startTimeToday.set(Calendar.HOUR_OF_DAY, schedulerConfig.getStartTime().getHour());
        startTimeToday.set(Calendar.MINUTE, schedulerConfig.getStartTime().getMinute());
        return timeToCheck.before(startTimeToday);
    }

    private boolean isAfterEndTime(Calendar timeToCheck) {
        Calendar endTimeToday = this.time;
        endTimeToday.set(Calendar.HOUR_OF_DAY, schedulerConfig.getEndTime().getHour());
        endTimeToday.set(Calendar.MINUTE, schedulerConfig.getEndTime().getMinute());
        return timeToCheck.after(endTimeToday);
    }

    private void setNextValidDay(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK) + 1);
        while (isInvalidDay(calendar)) {
            calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK) + 1);
        }
    }

    private boolean isInvalidDay(Calendar calendar) {
        // Saturdays are weekend days
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return true;
        }
        // Sundays are weekend days
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        }
        // 1st January is a bank holiday
        if (calendar.get(Calendar.DAY_OF_YEAR) == 1) {
            return true;
        }
        // 25th December is a bank holiday
        if (calendar.get(Calendar.MONTH) == 12 && calendar.get(Calendar.DAY_OF_MONTH) == 25){
            return true;
        }
        // 26th December is a bank holiday
        if (calendar.get(Calendar.MONTH) == 12 && calendar.get(Calendar.DAY_OF_MONTH) == 26){
            return true;
        }
        // TODO Handle May & August bank holidays, and handle Easter
        return false;
    }
}
