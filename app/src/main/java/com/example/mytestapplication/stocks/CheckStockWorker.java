package com.example.mytestapplication.stocks;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.mytestapplication.R;

public class CheckStockWorker extends Worker {

    private static final String CHANNEL_ID = "stock_channel_01";
    private static final String STOCK_CODE  = "MNZS";
    private static final double MIN_STOCK_PRICE = 210.0;
    private Stock mnzsStock;
    private final long wait = 1000 * 60;

    public CheckStockWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
        mnzsStock = new Stock(STOCK_CODE);
    }

    @Override
    public Result doWork() {

        // Do the work here--in this case, upload the images.
        try {
            checkStock();
            // Indicate whether the work finished successfully with the Result
            return Result.success();
        }
        catch (Exception ex) {
            return Result.failure();
        }
    }

    private void checkStock() throws Exception {
        String displayText;

            mnzsStock.refreshStockData();
            double stockPrice = mnzsStock.getPrice();
            //if (stockPrice > MIN_STOCK_PRICE) {
            displayText = String.valueOf(mnzsStock.getPrice());

            NotificationManager notificationManager = (NotificationManager)
                    getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
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
            //}
    }

    private Notification buildNotification(String text) {
        return new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle("MNZS Share Price")
                .setContentText("MNZS Share Price: " + text)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
    }
}
