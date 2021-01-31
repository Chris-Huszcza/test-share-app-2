package com.example.mytestapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.mytestapplication.services.CheckStockService;
import com.example.mytestapplication.stocks.CheckStockWorker;
import com.example.mytestapplication.stocks.Stock;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Button refreshTextBtn;
    Constraints constraints;
    WorkRequest workRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Context applicationContext = getApplicationContext();

        text = findViewById(R.id.textView);
        refreshTextBtn = findViewById(R.id.button);

        refreshTextBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (WifiChecker.isWifiEnabled(applicationContext)) {
                    text.setText("The text has changed.");
                }
                else {
                    text.setText("Wifi is not enabled - doing nothing.");
                }
            }
        });

        // Create Network constraint
        constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        //startService(new Intent(getBaseContext(), CheckStockService.class));
        workRequest =
                new PeriodicWorkRequest.Builder(CheckStockWorker.class, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();
    }

    public void startService(View view) {

        WorkManager
                .getInstance(getApplicationContext())
                .enqueue(workRequest);
        Toast.makeText(this, "Periodic work request started", Toast.LENGTH_LONG).show();
    }

    public void stopService(View view) {
        //stopService(new Intent(getBaseContext(), CheckStockService.class));
        WorkManager
                .getInstance(getApplicationContext())
                .cancelWorkById(workRequest.getId());
        Toast.makeText(this, "Periodic work request stopped", Toast.LENGTH_LONG).show();
    }
}