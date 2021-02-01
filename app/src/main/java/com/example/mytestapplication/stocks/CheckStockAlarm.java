package com.example.mytestapplication.stocks;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mytestapplication.R;

public class CheckStockAlarm extends BroadcastReceiver {

    private static final String CHANNEL_ID = "stock_channel_01";
    private static final String STOCK_CODE  = "MNZS";
    private static final double MIN_STOCK_PRICE = 200.0;
    private Stock mnzsStock;
    private final long wait = 1000 * 60;

    @Override
    public void onReceive(Context context, Intent intent) {
        String displayText;
        try {
            mnzsStock.refreshStockData();

            double stockPrice = mnzsStock.getPrice();
            if (stockPrice > MIN_STOCK_PRICE) {
                displayText = String.valueOf(mnzsStock.getPrice());

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

                Notification notification = buildNotification(context, displayText);
                notificationManager.notify(0, notification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Notification buildNotification(Context context, String text) {
        return new Notification.Builder(context, CHANNEL_ID)
                .setContentTitle("MNZS Share Price")
                .setContentText("MNZS Share Price: " + text)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
    }
}
