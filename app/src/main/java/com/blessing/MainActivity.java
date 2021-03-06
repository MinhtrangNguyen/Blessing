package com.blessing;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.blessing.services.AlarmReceiver;
import com.blessing.services.ServiceCallbacks;
import com.blessing.util.Constants;
import com.blessing.util.SharedPreferencesHandler;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.swt15Min)
    Switch swt15Min;
    @Bind(R.id.swtHour)
    Switch swtHour;
    @Bind(R.id.btnCounter)
    Button btnCounter;

    AlarmManager alarmManager;
    private PendingIntent pending_intent, pending_intent1;

    Calendar calendar;
    public Context context;
    Intent myIntent;
    private SharedPreferencesHandler spHandler;
    boolean repeat15Min = false, repeat1Hour = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.context = this;
        myIntent = new Intent(this.context, AlarmReceiver.class);
        spHandler = new SharedPreferencesHandler(this);

        // Get the alarm manager service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        repeat15Min = spHandler.getBooleanSavedPreferences("check15Min");
        repeat1Hour = spHandler.getBooleanSavedPreferences("check1Hour");

        setUI();

    }


    @OnClick(R.id.swt15Min)
    public void onClickSwt15Min() {
        if (swt15Min.isChecked()) {
            setPlayMedia(0);
            repeat15Min = true;
            spHandler.setPreferences("check15Min", repeat15Min);
            Toast.makeText(this, "Swt15Min On", Toast.LENGTH_SHORT).show();
        } else {
            StopMedia(0);
            repeat15Min = false;
            spHandler.setPreferences("check15Min", repeat15Min);
            Toast.makeText(this, "Swt15Min Off", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.swtHour)
    public void onClickSwtHour() {
        if (swtHour.isChecked()) {
            setPlayMedia(1);
            repeat1Hour = true;
            spHandler.setPreferences("check1Hour", repeat1Hour);
            Toast.makeText(this, "SwtHour On", Toast.LENGTH_SHORT).show();
        } else {
            StopMedia(1);
            repeat1Hour = false;
            spHandler.setPreferences("check1Hour", repeat1Hour);
            Toast.makeText(this, "SwtHour Off", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnCounter)
    public void onClickbtnCounter() {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(intent);
    }

    private void setUI() {
        if (repeat15Min) {
            swt15Min.setChecked(true);
        } else {
            swt15Min.setChecked(false);
        }
        if (repeat1Hour) {
            swtHour.setChecked(true);
        } else {
            swtHour.setChecked(false);
        }
    }

    private void setPlayMedia(int quote_id) {
        calendar = Calendar.getInstance();

        myIntent.putExtra("extra", "yes");
        myIntent.putExtra("quote_id", String.valueOf(quote_id));

        if (quote_id == 0) {
            myIntent.putExtra("ringtone", R.raw.e);
            pending_intent = PendingIntent.getBroadcast(MainActivity.this, quote_id, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 2 * 60 * 1000, pending_intent);
        }
        if (quote_id == 1) {
            myIntent.putExtra("ringtone", R.raw.subhan_wa_bh);
            pending_intent = PendingIntent.getBroadcast(MainActivity.this, quote_id, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5 * 60 * 1000, pending_intent);
        }
    }

    private void StopMedia(int quote_id) {
        myIntent.putExtra("extra", "no");
        myIntent.putExtra("quote_id", String.valueOf(quote_id));
        sendBroadcast(myIntent);

        pending_intent = PendingIntent.getBroadcast(MainActivity.this, quote_id, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pending_intent);
    }


}
