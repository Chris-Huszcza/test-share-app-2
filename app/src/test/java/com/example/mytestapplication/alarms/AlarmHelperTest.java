package com.example.mytestapplication.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;

import com.example.mytestapplication.stocks.CheckStockAlarm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class AlarmHelperTest {

    private Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void testStartAlarm() throws InterruptedException {
        AlarmHelper alarmHelper = new AlarmHelper(context);
        alarmHelper.setAlarm(100L);

        Thread.sleep(150);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, CheckStockAlarm.class);
        boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                intent,
                PendingIntent.FLAG_NO_CREATE) != null);
        assertTrue(alarmUp);
    }

    @Test
    public void testStopAlarm() {
        AlarmHelper alarmHelper = new AlarmHelper(context);
        alarmHelper.setAlarm(100L);

        alarmHelper.stopAlarm();
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, CheckStockAlarm.class);
        boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                intent,
                PendingIntent.FLAG_NO_CREATE) != null);
        assertFalse("Alarm appears to still be running", alarmUp);
    }
}
