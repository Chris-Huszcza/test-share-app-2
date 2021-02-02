package com.example.mytestapplication.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.mytestapplication.stocks.CheckStockAlarm;

public class AlarmHelper {

    private PendingIntent pendingIntent;
    private final AlarmManager alarmManager;
    private final Context context;

    public AlarmHelper(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarm(long timeInMillis) {
        Intent intent = new Intent(context, CheckStockAlarm.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        // 1000 milliseconds * 60 seconds * 1 minutes = 60,000
        long interval = 60000L;
        alarmManager.setRepeating(AlarmManager.RTC,timeInMillis,interval,pendingIntent);
    }

    public void stopAlarm() {
        alarmManager.cancel(pendingIntent);
    }
}
