package com.example.mytestapplication.alarms;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.example.mytestapplication.schedulers.Scheduler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowAlarmManager;
import org.robolectric.shadows.ShadowNotificationManager;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

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
    public void testScheduledAlarm() throws ClassNotFoundException {
        AlarmHelper alarmHelper = new AlarmHelper();
        alarmHelper.setAlarm(context);

        ShadowAlarmManager.ScheduledAlarm alarm = shadowAlarmManager.peekNextScheduledAlarm();

        assertNotNull(alarm);
    }

    @Test
    public void testStartAlarm() throws InterruptedException, ClassNotFoundException {
        AlarmHelper alarmHelper = new AlarmHelper();
        alarmHelper.setAlarm(context);

        boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                alarmHelper.getIntent(),
                PendingIntent.FLAG_NO_CREATE) != null);
        assertTrue(alarmUp);
    }

    @Test
    public void testStopAlarm() throws ClassNotFoundException {
        AlarmHelper alarmHelper = new AlarmHelper();
        alarmHelper.setAlarm(context);

        alarmHelper.stopAlarm(context);

        boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                alarmHelper.getIntent(),
                PendingIntent.FLAG_NO_CREATE) != null);

        assertFalse("Alarm appears to still be running", alarmUp);
    }

    @Test
    public void testGetNextEventTime() throws ClassNotFoundException {
        Scheduler scheduler = Mockito.mock(Scheduler.class);
        Calendar newEventTimeCalendar = Calendar.getInstance();
        newEventTimeCalendar.set(Calendar.MINUTE, 0);
        newEventTimeCalendar.set(Calendar.HOUR_OF_DAY, 8);
        when(scheduler.getNextEventTime(Mockito.any())).thenReturn(newEventTimeCalendar);

        AlarmHelper alarmHelper = new AlarmHelper(scheduler);
        alarmHelper.setAlarm(context);

        Calendar nextEventTime = alarmHelper.getNextEventTime();
        assertEquals(0, nextEventTime.get(Calendar.MINUTE));
        assertEquals(8, nextEventTime.get(Calendar.HOUR_OF_DAY));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetNextEventTimeNull() {
        AlarmHelper alarmHelper = new AlarmHelper();
        alarmHelper.getNextEventTime();
    }
}
