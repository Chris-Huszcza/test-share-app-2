package com.example.mytestapplication.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mytestapplication.R;
import com.example.mytestapplication.WifiChecker;
import com.example.mytestapplication.stocks.Stock;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class CheckStockService extends Service {

    private static final String CHANNEL_ID = "stock_channel_01";
    private Stock mnzsStock = new Stock("MNZS");

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Starting Service", Toast.LENGTH_LONG).show();

        if (checkSystemState()) {
            String displayText;
            try {
                mnzsStock.refreshStockData();
                displayText = mnzsStock.getPrice();
            } catch (Exception e) {
                displayText = "Exception: " + ExceptionUtils.getStackTrace(e);
            }

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            String name = "Share price updates";
            String description = "Changes to share prices";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel);
            //text.setText("MNZS price = " + displayText);
            Notification notification = buildNotification(displayText);
            notificationManager.notify(0, notification);
        }
        return START_STICKY;

    }

    private Notification buildNotification(String text) {
        return new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("MNZS Share Price")
                .setContentText("MNZS Share Price: " + text)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
    }

    private boolean checkSystemState() {
        // Check wifi is on
        if (!WifiChecker.isWifiEnabled(getApplicationContext())) {
            Toast.makeText(this, "Wifi is not enabled, stopping service", Toast.LENGTH_LONG).show();
            stopService(new Intent(getBaseContext(), CheckStockService.class));
            return false;
        }
        //
        return true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    }
}
