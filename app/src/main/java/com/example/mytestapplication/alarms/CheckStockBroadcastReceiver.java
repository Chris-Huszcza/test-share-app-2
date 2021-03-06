package com.example.mytestapplication.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mytestapplication.notifications.Notifier;
import com.example.mytestapplication.stocks.Stock;

public class CheckStockBroadcastReceiver extends BroadcastReceiver {

    private static final String STOCK_CODE  = "MNZS";
    private static final double MIN_STOCK_PRICE = 200.0;
    private final Stock mnzsStock = new Stock(STOCK_CODE);
    private static final AlarmHelper alarmHelper = new AlarmHelper();

    @Override
    public void onReceive(Context context, Intent intent) {
        // Set a new copy of this alarm to trigger after set interval.
        //AlarmHelper alarmHelper = new AlarmHelper(context);
        try {
            alarmHelper.setAlarm(context);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        checkStockAndNotify(context);
    }

    public void checkStockAndNotify(Context context) {
        try {
            mnzsStock.refreshStockData();
            double stockPrice = mnzsStock.getPrice();
            if (stockPrice > MIN_STOCK_PRICE) {
                String displayText = String.valueOf(mnzsStock.getPrice());

                Notifier notifier = new Notifier(context);
                notifier.notify(displayText);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
