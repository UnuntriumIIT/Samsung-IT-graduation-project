package com.example.dailydiary.dailydiaryalphav003;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class Loading extends AppCompatActivity {
    public static final String APP_PREFERENCES = "is_pass_excite";
    public static final String APP_BOOLEAN = "yes_no";
    private SharedPreferences mSettings;
    public Boolean yes_no=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        final Intent Start = new Intent(Loading.this, Launch.class);
        final Intent New = new Intent(Loading.this, CheckStart.class);
        yes_no = mSettings.getBoolean(APP_BOOLEAN, false);
        if (yes_no == true) {
            startActivity(Start);
            finish();}

        else {
            startActivity(New);
            finish();
        }
    }
}
