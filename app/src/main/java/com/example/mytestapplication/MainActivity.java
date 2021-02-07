package com.example.mytestapplication;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.WorkRequest;

import com.example.mytestapplication.alarms.AlarmHelper;

public class MainActivity extends AppCompatActivity {

    private AlarmHelper alarmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        alarmHelper = new AlarmHelper(getApplicationContext());
    }

    public void startService(View view) {
        alarmHelper.setAlarm();
        Toast.makeText(this, "Alarm started", Toast.LENGTH_LONG).show();
    }

    public void stopService(View view) {
        // Stop alarms
        alarmHelper.stopAlarm();
        Toast.makeText(this, "Alarm stopped", Toast.LENGTH_LONG).show();
        // Clear shared prefernces
    }
}