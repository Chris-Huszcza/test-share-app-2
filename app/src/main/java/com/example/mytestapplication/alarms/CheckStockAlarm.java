package com.example.mytestapplication.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mytestapplication.notifications.Notifier;
import com.example.mytestapplication.stocks.Stock;

public class CheckStockAlarm extends BroadcastReceiver {

    private static final String STOCK_CODE  = "MNZS";
    private static final double MIN_STOCK_PRICE = 200.0;
    private final Stock mnzsStock = new Stock(STOCK_CODE);

    @Override
    public void onReceive(Context context, Intent intent) {
        String displayText;
        // Set a new copy of this alarm to trigger after set interval.
        AlarmHelper alarmHelper = new AlarmHelper(context);
        alarmHelper.setAlarm();
        try {
            mnzsStock.refreshStockData();

            double stockPrice = mnzsStock.getPrice();
            if (stockPrice > MIN_STOCK_PRICE) {
                displayText = String.valueOf(mnzsStock.getPrice());

                Notifier notifier = new Notifier(context);
                notifier.notify(displayText);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
