package com.example.mytestapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytestapplication.services.CheckStockService;

public class MainActivity extends AppCompatActivity {

    TextView text = findViewById(R.id.textView);
    Button refreshTextBtn = findViewById(R.id.button);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context applicationContext = getApplicationContext();

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