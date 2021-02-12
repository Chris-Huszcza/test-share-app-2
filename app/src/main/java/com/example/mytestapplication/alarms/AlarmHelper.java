package com.example.mytestapplication.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.mytestapplication.schedulers.Scheduler;

import java.util.Calendar;

public class AlarmHelper {

    private Intent intent;
    private PendingIntent pendingIntent;
    private Scheduler scheduler;
    private static final String CHECK_STOCK_ALARM =
            "com.example.mytestapplication.alarms.CheckStockBroadcastReceiver";
    private Calendar nextEventTime;

    public AlarmHelper() {
        this.scheduler = new Scheduler();
    }

    public AlarmHelper(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setAlarm(Context context) throws ClassNotFoundException {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        nextEventTime = scheduler.getNextEventTime(Calendar.getInstance());

        intent = new Intent(context, CheckStockBroadcastReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                nextEventTime.getTimeInMillis(), pendingIntent);
    }

    // TODO implement this method after converting this class to a singlton
    /*public Calendar getNextEventTime() {
        if (this.nextEventTime == null) {
            throw new IllegalStateException("Next event time is null");
        }
        return this.nextEventTime;
    }*/

    public void stopAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE).cancel();
    }

    public Intent getIntent() {
        return this.intent;
    }

    public PendingIntent getPendingIntent() {
        return this.pendingIntent;
    }
}
