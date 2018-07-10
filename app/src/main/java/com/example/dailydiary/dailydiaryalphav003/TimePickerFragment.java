package com.example.dailydiary.dailydiaryalphav003;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.dailydiary.dailydiaryalphav003.settings.NOTIF_HOURS;
import static com.example.dailydiary.dailydiaryalphav003.settings.NOTIF_MINUTES;
import static com.example.dailydiary.dailydiaryalphav003.settings.NOTIF_PREFS;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    String main_hours = "";
    String main_mins = "";

    int a;
    int b;

    SharedPreferences mmm;

    Calendar notifyTime = Calendar.getInstance();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        Dialog picker = new TimePickerDialog(getActivity(), this, hour, minute, true);
        picker.setTitle("Выберите время");

        notifyTime.setLenient(false);
        notifyTime.getTime();

        return picker;
    }

    @Override
    public void onStart() {
        super.onStart();
        Button nButton = ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE);
        nButton.setText("Готово");
    }

    @Override
    public void onTimeSet(TimePicker view, int hours, int minute) {

        TextView tv = (TextView) getActivity().findViewById(R.id.time_pick);

        String mm = "";
        if (minute<10){
            switch (minute) {
                case 0:mm = "00";break;
                case 1:mm = "01";break;
                case 2:mm = "02";break;
                case 3:mm = "03";break;
                case 4:mm = "04";break;
                case 5:mm = "05";break;
                case 6:mm = "06";break;
                case 7:mm = "07";break;
                case 8:mm = "08";break;
                case 9:mm = "09";break;
            }
        }
        else mm = String.valueOf(minute);
        tv.setText("Будем напоминать Вам в " + hours + ":" + mm);
        main_hours = String.valueOf(hours);
        main_mins = mm;
        a = hours;
        b = minute;
    }
    public void onStop(){
        super.onStop();
        mmm = getActivity().getSharedPreferences(NOTIF_PREFS, Context.MODE_PRIVATE);

        notifyTime.clear();

        notifyTime.add(Calendar.HOUR_OF_DAY, a);
        notifyTime.add(Calendar.MINUTE, b);
        notifyTime.setLenient(false);

        Intent intent = new Intent(getActivity(), MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, notifyTime.getTimeInMillis(), 86400000, pendingIntent);

        SharedPreferences.Editor fff = mmm.edit();
        fff.putString(NOTIF_HOURS, main_hours);
        fff.putString(NOTIF_MINUTES, main_mins);
        fff.apply();
        fff.commit();


    }
}
