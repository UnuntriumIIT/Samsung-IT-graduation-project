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
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;

public class Launch extends AppCompatActivity {
    public static final String APP_PREFERENCES = "is_pass_excite";
    public static final String APP_PASSWORD = "pas";
    public static final String APP_HINT = "hint";
    private SharedPreferences mSettings;
    protected String pass;
    int z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("Log in");
        LinearLayout log_in = (LinearLayout) findViewById(R.id.login_layout);

        ActionBar bar = getSupportActionBar();
        bar.hide();


        z = (int)(Math.random()*10);

        switch (z) {
            case 0:log_in.setBackgroundResource(R.drawable.voda); break;
            case 1:log_in.setBackgroundResource(R.drawable.eeeeerok);break;
            case 2:log_in.setBackgroundResource(R.drawable.kekekek);break;
            case 3:log_in.setBackgroundResource(R.drawable.j1j1j);break;
            case 4:log_in.setBackgroundResource(R.drawable.wood);break;
            case 5:log_in.setBackgroundResource(R.drawable.mood);break;
            case 6:log_in.setBackgroundResource(R.drawable.mountain);break;
            case 7:log_in.setBackgroundResource(R.drawable.most);break;
            case 8:log_in.setBackgroundResource(R.drawable.bereg);break;
            case 9:log_in.setBackgroundResource(R.drawable.water);break;
        }

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Button login = (Button)findViewById(R.id.login_button);
        final EditText passwords = (EditText)findViewById(R.id.edit_text_login);
        final Intent Main = new Intent(Launch.this, MainActivity.class);
        Button hint = (Button)findViewById(R.id.help_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pass = passwords.getText().toString();
                if (pass.equals(mSettings.getString(APP_PASSWORD, pass))) {
                    finish();
                    startActivity(Main);
                }
                else {
                    Toast wrong = Toast.makeText(getApplicationContext(), "Неправильно, попробуйте снова!", Toast.LENGTH_SHORT);
                    wrong.show();
                }
            }
        });

        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast hint = Toast.makeText(getApplicationContext(), mSettings.getString(APP_HINT, "") , Toast.LENGTH_SHORT);
                hint.show();
            }
        });
    }
}
