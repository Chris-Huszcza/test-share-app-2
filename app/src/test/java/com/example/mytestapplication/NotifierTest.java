package com.example.mytestapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import androidx.test.core.app.ApplicationProvider;

import com.example.mytestapplication.notifications.Notifier;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowNotificationManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@Config(
        shadows = {ShadowNotificationManager.class}
)
@RunWith(RobolectricTestRunner.class)
public class NotifierTest {

    private final Context context = ApplicationProvider.getApplicationContext();


    @Test
    public void testNotify() {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        ShadowNotificationManager shadowNotificationManager = Shadows.shadowOf(notificationManager);

        int preNotifs = shadowNotificationManager.getAllNotifications().size();
        assertEquals(0, preNotifs);

        Notifier notifier = new Notifier(context);
        notifier.notify("230.00");

        int postNotifs = shadowNotificationManager.getAllNotifications().size();
        assertTrue(postNotifs > 0);

    }

    @Test
    public void testBuildNotification() {
        Notification.Builder builder = new Notification.Builder(context, Notifier.CHANNEL_ID);
        Notification.Builder builderSpy = Mockito.spy(builder);
        Notifier notifier = new Notifier(context, builderSpy);
        Notification notification = notifier.buildNotification("SomeTitle", "SomeText");
        assertNotNull(notification);
        verify(builderSpy).build();
    }

}
