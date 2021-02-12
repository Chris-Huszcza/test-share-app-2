package com.example.mytestapplication.alarms;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowAlarmManager;
import org.robolectric.shadows.ShadowNotificationManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class AlarmHelperTest {

    private ShadowAlarmManager shadowAlarmManager;
    private final Context context = ApplicationProvider.getApplicationContext();

    @Before
    public void setUp() {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        shadowAlarmManager = Shadows.shadowOf(alarmManager);
    }

    @Test
    public void testScheduledAlarm() {
        AlarmHelper alarmHelper = new AlarmHelper(context);
        alarmHelper.setAlarm();

        ShadowAlarmManager.ScheduledAlarm alarm = shadowAlarmManager.peekNextScheduledAlarm();

        assertNotNull(alarm);
    }

    @Test
    public void testStartAlarm() throws InterruptedException {
        AlarmHelper alarmHelper = new AlarmHelper(context);
        alarmHelper.setAlarm();

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
