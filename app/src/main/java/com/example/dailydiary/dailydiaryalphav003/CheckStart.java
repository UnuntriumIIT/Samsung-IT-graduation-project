package com.example.dailydiary.dailydiaryalphav003;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CheckStart extends AppCompatActivity {
    public static final String APP_PREFERENCES = "is_pass_excite";
    public static final String APP_BOOLEAN = "yes_no";
    public static final String APP_PASSWORD = "pas";
    public static final String APP_HINT = "hint";
    private SharedPreferences mSettings;
    public Boolean yes_no=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_start);
        final Intent Launch = new Intent(CheckStart.this, Launch.class);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        yes_no = mSettings.getBoolean(APP_BOOLEAN, false);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("Sign up");

        ActionBar bar = getSupportActionBar();
        bar.hide();

        if (yes_no == false) {
            Toast toast = Toast.makeText(getApplicationContext(), "Придумайте пароль от дневника!", Toast.LENGTH_SHORT);
            toast.show();
        }

        final EditText pass_edit = (EditText)findViewById(R.id.et_add_password);
        final Button add = (Button) findViewById(R.id.btn_add_password);
        final EditText help = (EditText) findViewById(R.id.help_hint);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pas = pass_edit.getText().toString();
                String hint = help.getText().toString();
                if (pas != "") {
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(APP_PASSWORD, pas);
                    editor.putString(APP_HINT, hint);
                    editor.commit();
                    editor.apply();
                    Toast toast = Toast.makeText(getApplicationContext(), "Успешно!", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                    startActivity(Launch);
                    yes_no = true;
                }
                else {
                   Toast toast = Toast.makeText(getApplicationContext(),"Try Again!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(APP_BOOLEAN, yes_no);
        editor.commit();
        editor.apply();
    }
}
