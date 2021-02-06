package com.example.mytestapplication.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import com.example.mytestapplication.R;

public class Notifier {

    private final Notification.Builder builder;
    private final Context context;
    public static final String CHANNEL_ID = "stock_channel_01";
    private static final String NOTIFICATION_TITLE = "MNZS Stock Price";
    private static final String NOTIFICATION_PREFIX = "MNZS Stock Price: ";

    public Notifier(Context context) {
        this.context = context;
        this.builder = new Notification.Builder(context, CHANNEL_ID);
    }

    public Notifier(Context context, Notification.Builder builder) {
        this.context = context;
        this.builder = builder;
    }

    public void notify(String price) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        String name = "Share price updates";
        String description = "Changes to share prices";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        notificationManager.createNotificationChannel(channel);

        Notification notification = buildNotification(NOTIFICATION_TITLE,
                NOTIFICATION_PREFIX + price);
        notificationManager.notify(0, notification);
    }

    public Notification buildNotification(String title, String text) {
        return builder
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
    }
}
