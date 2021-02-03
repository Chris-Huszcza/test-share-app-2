package com.example.mytestapplication.alarms;

import android.app.PendingIntent;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class AlarmHelperTest {

    private final Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void testStartAlarm() throws InterruptedException {
        AlarmHelper alarmHelper = new AlarmHelper(context);
        alarmHelper.setAlarm();//100L);

        boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                alarmHelper.getIntent(),
                PendingIntent.FLAG_NO_CREATE) != null);
        assertTrue(alarmUp);
    }

    @Test
    public void testStopAlarm() {
        AlarmHelper alarmHelper = new AlarmHelper(context);
        alarmHelper.setAlarm();

        alarmHelper.stopAlarm();

        boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                alarmHelper.getIntent(),
                PendingIntent.FLAG_NO_CREATE) != null);

        assertFalse("Alarm appears to still be running", alarmUp);
    }
}
