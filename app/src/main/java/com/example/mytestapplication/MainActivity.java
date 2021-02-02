package com.example.mytestapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import com.example.mytestapplication.stocks.CheckStockAlarm;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Button refreshTextBtn;
    Constraints constraints;
    WorkRequest workRequest;
    AlarmHelper alarmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Context applicationContext = getApplicationContext();

        text = findViewById(R.id.textView);

        alarmHelper = new AlarmHelper(getApplicationContext());
    }

    public void startService(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND + 4));

        //setAlarm(calendar.getTimeInMillis());
        alarmHelper.setAlarm(calendar.getTimeInMillis());
        Toast.makeText(this, "Alarm started", Toast.LENGTH_LONG).show();
    }

    /*private void setAlarm(long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, CheckStockAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        // 1000 milliseconds * 60 seconds * 1 minutes = 60,000
        long interval = 60000L;
        alarmManager.setRepeating(AlarmManager.RTC, timeInMillis, interval, pendingIntent);

        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();

    }*/

    public void stopService(View view) {
        alarmHelper.stopAlarm();
        Toast.makeText(this, "Alarm stopped", Toast.LENGTH_LONG).show();
    }
}