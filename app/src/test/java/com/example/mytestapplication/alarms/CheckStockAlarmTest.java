package com.example.mytestapplication.alarms;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.test.mock.MockContext;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowNotificationManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class CheckStockAlarmTest {

    private ShadowNotificationManager shadowNotificationManager;
    private final Context context = ApplicationProvider.getApplicationContext();
    private static final String CHECK_STOCK_ALARM =
            "com.example.mytestapplication.alarms.CheckStockAlarm";

    @Before
    public void setUp() {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        shadowNotificationManager = Shadows.shadowOf(notificationManager);
    }

    @Test
    public void testOnReceive() {
        // Check there are no notifications.
        int preNotifs = shadowNotificationManager.getAllNotifications().size();
        assertEquals(0, preNotifs);

        CheckStockAlarm checkStockAlarm = new CheckStockAlarm();
        Intent intent = new Intent(CHECK_STOCK_ALARM);
        checkStockAlarm.onReceive(context, intent);

        // Check there is an alarm registered
        boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                intent,
                PendingIntent.FLAG_NO_CREATE) != null);
        assertTrue(alarmUp);

        // Check there was a notification
        int postNotifs = shadowNotificationManager.getAllNotifications().size();
        assertEquals(1, postNotifs);
    }

    @Test
    public void testWithMockContext() {
        MockContext mockContext = new MockContext();
        //mockContext.
    }

    @Test
    public void testCheckStock() {

    }
}
