package com.example.mytestapplication;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytestapplication.alarms.AlarmHelper;
import com.example.mytestapplication.stocks.Stock;

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
        String alarmTime;
        try {
            Calendar nextEventTime = alarmHelper.getNextEventTime();
            alarmTime = nextEventTime.getTime().toGMTString();
        } catch (IllegalStateException ex) {
            alarmTime = "No alarm set.";
        }
        textView.setText("Next Alarm is set for: " + alarmTime);
    }

    public void getStockInfo(View view) {
        TextView textView = (TextView)findViewById(R.id.textView);
        String stockValue;
        try {
            Stock mnzsStock = new Stock("MNZS");
            mnzsStock.refreshStockData();
            stockValue = String.valueOf(mnzsStock.getPrice());
        }
        catch (Exception ex) {
            stockValue = "Exception when trying to get stock value";
        }
        textView.setText("MNZS price = " + stockValue);
    }
}