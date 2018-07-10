package com.example.dailydiary.dailydiaryalphav003;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        conNotifications.notify(context);
    }
}
