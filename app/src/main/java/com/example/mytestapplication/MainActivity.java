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

import com.example.mytestapplication.services.CheckStockService;
import com.example.mytestapplication.stocks.Stock;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Button refreshTextBtn;

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
    }

    public void startService(View view) {
        startService(new Intent(getBaseContext(), CheckStockService.class));
    }

    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), CheckStockService.class));
    }
}