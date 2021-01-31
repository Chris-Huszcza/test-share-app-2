package com.example.mytestapplication.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mytestapplication.WifiChecker;
import com.example.mytestapplication.stocks.Stock;

public class CheckStockService extends Service {

    private static final String CHANNEL_ID = "stock_channel_01";
    private static final String STOCK_CODE  = "MNZS";
    private static final double MIN_STOCK_PRICE = 210.0;
    private final Stock mnzsStock = new Stock(STOCK_CODE);
    private final long wait = 1000 * 60;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Starting Service", Toast.LENGTH_LONG).show();

        performOnBackgroundThread(new CheckStockRunnable(getApplicationContext()));

        return START_STICKY;
    }

    private void performOnBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                runnable.run();
                Toast.makeText(getApplicationContext(), "runnable running", Toast.LENGTH_LONG).show();
            }
        };
        Toast.makeText(getApplicationContext(), "Thread starting", Toast.LENGTH_LONG).show();
        t.start();
        Toast.makeText(getApplicationContext(), "Thread started", Toast.LENGTH_LONG).show();
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
