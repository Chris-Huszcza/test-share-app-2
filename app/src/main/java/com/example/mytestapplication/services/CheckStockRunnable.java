package com.example.mytestapplication.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.mytestapplication.R;
import com.example.mytestapplication.WifiChecker;
import com.example.mytestapplication.stocks.Stock;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class CheckStockRunnable implements Runnable {

    private Context context;

    private static final String CHANNEL_ID = "stock_channel_01";
    private static final String STOCK_CODE  = "MNZS";
    private static final double MIN_STOCK_PRICE = 210.0;
    private final Stock mnzsStock = new Stock(STOCK_CODE);
    private final long wait = 1000 * 60;

    public CheckStockRunnable(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        Toast.makeText(this.context, "Thread running", Toast.LENGTH_LONG).show();
        while (checkSystemState()) {
            String displayText;
            try {
                mnzsStock.refreshStockData();
                double stockPrice = mnzsStock.getPrice();
                if (stockPrice > MIN_STOCK_PRICE) {
                    displayText = String.valueOf(mnzsStock.getPrice());

                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    String name = "Share price updates";
                    String description = "Changes to share prices";
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                    channel.setDescription(description);
                    // Register the channel with the system; you can't change the importance
                    // or other notification behaviors after this
                    notificationManager.createNotificationChannel(channel);

                    Notification notification = buildNotification(displayText);
                    notificationManager.notify(0, notification);
                    Toast.makeText(this.context, "Service stopping", Toast.LENGTH_LONG).show();
                    this.context.stopService(new Intent(this.context, CheckStockService.class));
                }
                Thread.sleep(wait);
            }
            catch (Exception e) {
                String exceptionText = "Exception in service: " + ExceptionUtils.getStackTrace(e);
                Toast.makeText(this.context, exceptionText, Toast.LENGTH_LONG).show();
                this.context.stopService(new Intent(this.context, CheckStockService.class));
            }
        }
    }

    private Notification buildNotification(String text) {
        return new Notification.Builder(this.context, CHANNEL_ID)
                .setContentTitle("MNZS Share Price")
                .setContentText("MNZS Share Price: " + text)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
    }

    private boolean checkSystemState() {
        // Check wifi is on
        if (!WifiChecker.isWifiEnabled(this.context.getApplicationContext())) {
            Toast.makeText(this.context, "Wifi is not enabled, stopping service", Toast.LENGTH_LONG).show();
            this.context.stopService(new Intent(this.context, CheckStockService.class));
            return false;
        }
        //
        return true;
    }
}
