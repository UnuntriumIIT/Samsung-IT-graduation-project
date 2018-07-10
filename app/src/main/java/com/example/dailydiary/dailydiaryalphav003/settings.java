package com.example.dailydiary.dailydiaryalphav003;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class settings extends AppCompatActivity {

    public static final String NOTIF_PREFS = "notification_prefs";
    public static final String MAIN_SWITCH = "main_switch";
    public static final String NOTIF_HOURS = "notification_hours";
    public static final String NOTIF_MINUTES = "notification_minutes";

    Boolean prefs_switch = true;
    SharedPreferences prefs;
    AlarmManager alarmManager;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);
        setTitle("Настройки уведомлений");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        final Switch main_switch = (Switch) findViewById(R.id.not_switch);
        final TextView timepick = (TextView) findViewById(R.id.time_pick);

        prefs = getSharedPreferences(NOTIF_PREFS, Context.MODE_PRIVATE);

        prefs_switch = prefs.getBoolean(MAIN_SWITCH, true);

        if (prefs_switch == true & prefs.contains(NOTIF_MINUTES) & prefs.contains(NOTIF_HOURS)) {
            String hours = prefs.getString(NOTIF_HOURS, null);
            String minute = prefs.getString(NOTIF_MINUTES, null);

            timepick.setText("Будем напоминать Вам в " + hours + ":" + minute);
        };

        timepick.setClickable(false);

        if (prefs_switch == false) {
            main_switch.setChecked(false);
            timepick.setClickable(false);
            timepick.setText("Уведомления выключены.");

            SharedPreferences.Editor tt = prefs.edit();
            tt.clear();
            tt.apply();

            alarmManager = (AlarmManager) settings.this.getSystemService(Context.ALARM_SERVICE);
            Intent notifyIntent = new Intent(context,MyReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notifyIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.cancel(pendingIntent);

        } else {
            main_switch.setChecked(true);
            timepick.setClickable(true);
        }
        main_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    prefs_switch = true;
                    timepick.setClickable(true);
                    timepick.setText("Во сколько напоминать?");
                } else {
                    prefs_switch = false;
                    timepick.setClickable(false);
                    SharedPreferences.Editor fdfs = prefs.edit();
                    fdfs.remove(NOTIF_HOURS);
                    fdfs.remove(NOTIF_MINUTES);
                    fdfs.apply();
                    fdfs.commit();
                    timepick.setText("Уведомления выключены.");

                    alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    Intent notifyIntent = new Intent(context,MyReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notifyIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                    alarmManager.cancel(pendingIntent);
                }
            }
        });
        timepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences.Editor lul = prefs.edit();
        lul.putBoolean(MAIN_SWITCH, prefs_switch);
        lul.apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
