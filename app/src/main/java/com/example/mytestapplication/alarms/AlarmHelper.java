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
    private final AlarmManager alarmManager;
    private final Context context;
    private static final String CHECK_STOCK_ALARM =
            "com.example.mytestapplication.alarms.CheckStockAlarm";
    private Calendar nextEventTime;

    public AlarmHelper(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.scheduler = new Scheduler();
    }

    public void setAlarm() {
        nextEventTime = scheduler.getNextEventTime(Calendar.getInstance());

        intent = new Intent(CHECK_STOCK_ALARM);

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                nextEventTime.getTimeInMillis(), pendingIntent);
    }

    public Calendar getNextEventTime() {
        if (this.nextEventTime == null) {
            throw new IllegalStateException("Next event time is null");
        }
        return this.nextEventTime;
    }

    public void stopAlarm() {
        Intent intent = new Intent(CHECK_STOCK_ALARM);
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

    public AlarmManager getAlarmManager() {
        return this.alarmManager;
    }
}
