package com.example.mytestapplication;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytestapplication.alarms.AlarmHelper;

import java.util.Calendar;

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

    /*public void openSettings(View view) {
        clickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this,
                        SecondActivity.class);
                startActivity(intent);
            }
        });
    }*/

    public void startService(View view) {
        alarmHelper.setAlarm();
        Toast.makeText(this, "Alarm started", Toast.LENGTH_LONG).show();
    }

    public void stopService(View view) {
        // Stop alarms
        alarmHelper.stopAlarm();
        Toast.makeText(this, "Alarm stopped", Toast.LENGTH_LONG).show();
        // Clear shared preferences
    }

    public void refreshInfo(View view) {
        TextView textView = (TextView)findViewById(R.id.textView);
        Calendar nextEventTime = alarmHelper.getNextEventTime();
        String alarmTime = nextEventTime.getTime().toGMTString();
        textView.setText("Next Alarm is set for: " + alarmTime);
    }
}