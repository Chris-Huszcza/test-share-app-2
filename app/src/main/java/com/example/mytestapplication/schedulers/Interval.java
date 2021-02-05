package com.example.mytestapplication.schedulers;

import android.icu.util.TimeUnit;

public class Interval {

    private int timeUnit;
    private int amount;

    /**
     *
     * @param timeUnit - Use Calendar static fields to set the timeunit
     * @param amount
     */
    public Interval(int timeUnit, int amount) {
        this.timeUnit = timeUnit;
        this.amount = amount;
    }

    public int getTimeUnit() {
        return this.timeUnit;
    }

    public int getAmount() {
        return this.amount;
    }
}
