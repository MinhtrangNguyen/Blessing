package com.blessing.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.blessing.util.WakeLocker;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getExtras().getString("extra");
        Log.e("MyActivity", "In the receiver with " + state);
        int ringtone = intent.getExtras().getInt("ringtone");

        String quote_id = intent.getExtras().getString("quote_id");
        Log.e("Richard quote is" , quote_id);

        Intent serviceIntent = new Intent(context,RingtonePlayingService.class);
        serviceIntent.putExtra("extra", state);
        serviceIntent.putExtra("quote_id", quote_id);
        serviceIntent.putExtra("ringtone", ringtone);

        context.startService(serviceIntent);

        WakeLocker.acquire(context);
    }

}
