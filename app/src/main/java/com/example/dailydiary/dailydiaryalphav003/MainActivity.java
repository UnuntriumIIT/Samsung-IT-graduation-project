package com.example.dailydiary.dailydiaryalphav003;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.example.dailydiary.dailydiaryalphav003.Launch.APP_HINT;
import static com.example.dailydiary.dailydiaryalphav003.Launch.APP_PASSWORD;
import static com.example.dailydiary.dailydiaryalphav003.Launch.APP_PREFERENCES;
import static com.example.dailydiary.dailydiaryalphav003.settings.NOTIF_PREFS;

public class MainActivity extends AppCompatActivity {
    private ArrayList<PostData> postData = new ArrayList<>();
    private ArrayList<Integer> ratesdays = new ArrayList<>();
    private ArrayList<String> datedays = new ArrayList<>();

    String what_img;
    int what_str=0;

    postItemAdapter itemAdapter;

    int co1=0;
    int co2=0;
    int co3=0;
    int co4=0;
    int co5=0;

    int tab_launch = 0;

    private SharedPreferences mTab;
    public final String TAB_MEMORY = "tabs";
    public final String TAB_NUMBER = "tab_num";

    DateFormat day_time = new SimpleDateFormat("EEE", Locale.ENGLISH);
    DateFormat date_time = new SimpleDateFormat("dd.MM");
    String date_main = date_time.format(new Date());
    String day_result;
    String day_main = day_time.format(new Date());

    public static final String DELETE_POST_DATA = "post_delete";
    public static final String BINARY_DELETE_KEY = "delete_key";
    SharedPreferences delete_m;
    SharedPreferences change;
    SharedPreferences not;
    Boolean del_key = false;

    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        delete_m = getSharedPreferences(DELETE_POST_DATA, Context.MODE_PRIVATE);
        change = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        not = getSharedPreferences(NOTIF_PREFS, Context.MODE_PRIVATE);

        del_key = delete_m.getBoolean(BINARY_DELETE_KEY, false);

        mTab = getSharedPreferences(TAB_MEMORY, Context.MODE_PRIVATE);
        if (mTab.contains(TAB_NUMBER)) {
            tab_launch = mTab.getInt(TAB_NUMBER, 0);
        }
        if (getPreferences(MODE_PRIVATE).contains("postData")) {
            Type type = new TypeToken<ArrayList<PostData>>(){}.getType();
            Gson gson = new Gson();
            String json = getPreferences(MODE_PRIVATE).getString("postData", null);
            postData = gson.fromJson(json, type);
        }

        for(int i = 0; i<postData.size(); i++) {
            if (postData.get(i).getWhat_img().equals("1")){
                co1++;
            }
            if (postData.get(i).getWhat_img().equals("2")){
                co2++;
            }
            if (postData.get(i).getWhat_img().equals("3")){
                co3++;
            }
            if (postData.get(i).getWhat_img().equals("4")){
                co4++;
            }
            if (postData.get(i).getWhat_img().equals("5")){
                co5++;
            }
        }

        setTitle("Daily Diary");

        final TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Записи");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Записи");
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Статистика");
        tabHost.addTab(tabSpec);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                switch (tabHost.getCurrentTab()) {
                    case 0: tab_launch=0; break;
                    case 1: tab_launch=1; break;
                }
            }
        });
        SharedPreferences.Editor tab_ed = mTab.edit();
        tab_ed.putInt(TAB_NUMBER, tab_launch);
        tab_ed.commit();
        tab_ed.apply();

        tabHost.setCurrentTab(tab_launch);

        LinearLayout listlayout = (LinearLayout) findViewById(R.id.tab1);
        LinearLayout layout = (LinearLayout) findViewById(R.id.tab2);

        listlayout.setPadding(5,5,5,0);

        switch(day_main) {
            case "Mon": day_result = "ПН"; break;
            case "Tue": day_result = "ВТ"; break;
            case "Wed": day_result = "СР"; break;
            case "Thu": day_result = "ЧТ"; break;
            case "Fri": day_result = "ПТ"; break;
            case "Sat": day_result = "СБ"; break;
            case "Sun": day_result = "ВС"; break;
            default: day_result = "null"; break;
        }

        ListView listView = (ListView)this.findViewById(R.id.list_view_main);
        itemAdapter = new postItemAdapter(this, postData);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alert_dialog = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inf = LayoutInflater.from(MainActivity.this);
                View prompt = inf.inflate(R.layout.alert_listview, null);
                alert_dialog.setCancelable(true);
                alert_dialog.setView(prompt);
                alert_dialog.create();
                final AlertDialog show = alert_dialog.show();
                final int pos = i;
                int where = pos+1;
                final String wher = String.valueOf(where);

                Button delete = (Button) prompt.findViewById(R.id.alert_delete_btn);
                Button edit = (Button) prompt.findViewById(R.id.alert_edit_btn);

                delete.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        postData.remove(pos);
                        SharedPreferences.Editor sharedPreferences = getPreferences(MODE_PRIVATE).edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(postData);
                        sharedPreferences.putString("postData", json);
                        sharedPreferences.commit();
                        sharedPreferences.apply();
                        itemAdapter.notifyDataSetChanged();
                        show.dismiss();
                    }
                });
                edit.setOnClickListener(new AdapterView.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        show.dismiss();
                        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                        View prom = inflater.inflate(R.layout.edit_alert, null);
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                        alert.setCancelable(false);
                        alert.setView(prom);
                        alert.create();
                        final AlertDialog show_edit = alert.show();
                        final RatingBar rating_edit = (RatingBar) prom.findViewById(R.id.rating_bar_edit);
                        final EditText et1_edit =(EditText) prom.findViewById(R.id.title_edit);
                        final EditText et2_edit = (EditText) prom.findViewById(R.id.underTitle_edit);
                        final Button btnAdd_edit = (Button) prom.findViewById(R.id.btnAdd_edit);
                        final Button btnAdd_cancel = (Button) prom.findViewById(R.id.btnAdd_cancel);
                        final TextView tw_1 = (TextView) findViewById(R.id.main_title);
                        final TextView tw_2 = (TextView) findViewById(R.id.main_description);
                        String tw1 = tw_1.getText().toString();
                        String tw2 = tw_2.getText().toString();
                        et1_edit.setText(tw1);
                        et2_edit.setText(tw2);
                        rating_edit.setRating(1.0f);
                        btnAdd_edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String one = et1_edit.getText().toString();
                                String two = et2_edit.getText().toString();
                                what_str = (int)rating_edit.getRating();
                                what_img = String.valueOf(what_str);
                                if (one.equals("")||two.equals("")) {
                                    Toast.makeText(getApplicationContext(), "Не всё заполнено!", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    PostData data1 = new PostData(one, two, what_img, date_main, day_result);
                                    postData.set(pos, data1);
                                    itemAdapter.notifyDataSetChanged();
                                    SharedPreferences.Editor sharedPreferences = getPreferences(MODE_PRIVATE).edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(postData);
                                    sharedPreferences.putString("postData", json);
                                    sharedPreferences.commit();
                                    sharedPreferences.apply();

                                    show_edit.dismiss();
                                    Toast.makeText(getApplicationContext(), "Успешно!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        btnAdd_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                show_edit.cancel();
                            }
                        });
                    }
                });
            }
        });

        PieChart mPieChart = (PieChart) findViewById(R.id.piechart);

        if(postData.isEmpty()==false) {
            mPieChart.addPieSlice(new PieModel("Отлично", co5, Color.parseColor("#F28B12")));
            mPieChart.addPieSlice(new PieModel("Хорошо", co4, Color.parseColor("#409E60")));
            mPieChart.addPieSlice(new PieModel("Ну такое...", co3, Color.parseColor("#8B59A0")));
            mPieChart.addPieSlice(new PieModel("Плохо", co2, Color.parseColor("#5979A0")));
            mPieChart.addPieSlice(new PieModel("Ужасно", co1, Color.parseColor("#586A74")));
        }
        else mPieChart.addPieSlice(new PieModel("Данных пока нет", 0, Color.parseColor("#FFFFFF")));

        mPieChart.startAnimation();

        if (postData.isEmpty() == false) {
            for (int i = 0; i < postData.size(); i++) {
                ratesdays.add(0, Integer.valueOf(postData.get(i).getWhat_img()));
                datedays.add(0, postData.get(i).getDate_main());
            }
        }

        ValueLineChart mCubicValueLineChart = (ValueLineChart) findViewById(R.id.cubiclinechart);
        mCubicValueLineChart.setPadding(20,10,20,0);
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);

        if (datedays.isEmpty() == false & ratesdays.isEmpty() == false & postData.size() >= 2) {
            for (int k = 0; k < postData.size(); k++) {
                String dd = String.valueOf(datedays.get(k));
                int jjj = ratesdays.get(k);
                series.addPoint(new ValueLinePoint(dd, jjj));
            }
        }
        else {
            series.addPoint(new ValueLinePoint("Данных пока нет", 0));
            series.addPoint(new ValueLinePoint("Данных пока нет", 0));
            series.addPoint(new ValueLinePoint("Данных пока нет", 0));
        }

        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu_kek, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.about_me:
                startActivity(new Intent(this, about.class));
                break;
            case R.id.pen:
                LayoutInflater inflater = LayoutInflater.from(this);
                final View promptView = inflater.inflate(R.layout.alert_dialog, null);

                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                alertDialog.setView(promptView);
                alertDialog.show();

                final RatingBar ratings = (RatingBar) promptView.findViewById(R.id.ratingbar);
                final EditText et1 =(EditText) promptView.findViewById(R.id.titlee);
                final EditText et2 = (EditText) promptView.findViewById(R.id.underTitle);
                final Button btnAdd = (Button) promptView.findViewById(R.id.btnAdd);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        what_str = (int)ratings.getRating();
                        what_img = String.valueOf(what_str);
                        String title1 = et1.getText().toString();
                        String title2 = et2.getText().toString();
                        PostData data = new PostData(title1, title2, what_img, date_main, day_result);
                        postData.add(0, data);
                        SharedPreferences.Editor sharedPreferences = getPreferences(MODE_PRIVATE).edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(postData);
                        sharedPreferences.putString("postData", json);
                        sharedPreferences.commit();
                        sharedPreferences.apply();
                        itemAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });
                break;
            case R.id.refresh:
                reload();
                break;
            case R.id.delete_all:
                AlertDialog.Builder delete_dialog = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater set_infl = LayoutInflater.from(MainActivity.this);
                View set_view = set_infl.inflate(R.layout.test, null);

                delete_dialog.setCancelable(true);
                delete_dialog.setView(set_view);
                delete_dialog.create();
                final AlertDialog showsss = delete_dialog.show();

                Button yes = (Button) set_view.findViewById(R.id.yes_btn);
                Button no = (Button) set_view.findViewById(R.id.no_btn);

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showsss.cancel();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        postData.clear();
                        ratesdays.clear();
                        datedays.clear();

                        Intent close = new Intent(MainActivity.this, Loading.class);

                        ValueLineChart mCubicValueLineChart = (ValueLineChart) findViewById(R.id.cubiclinechart);
                        PieChart mPieChart = (PieChart) findViewById(R.id.piechart);

                        mCubicValueLineChart.clearChart();
                        mPieChart.clearChart();

                        SharedPreferences.Editor a = getPreferences(MODE_PRIVATE).edit();
                        SharedPreferences.Editor b = delete_m.edit();
                        SharedPreferences.Editor c = change.edit();
                        SharedPreferences.Editor d = not.edit();

                        a.clear();
                        b.clear();
                        c.clear();
                        d.clear();

                        a.apply();
                        b.apply();
                        c.apply();
                        d.apply();

                        showsss.dismiss();

                        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent notifyIntent = new Intent(MainActivity.this, MyReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, notifyIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                        alarmManager.cancel(pendingIntent);

                        finish();
                        startActivity(close);
                    }
                });
                break;

            case R.id.notific_set:
                Intent not_set = new Intent(this, settings.class);
                startActivity(not_set);
                break;
            case R.id.change_pass:
                LayoutInflater infla = LayoutInflater.from(this);
                View chan = infla.inflate(R.layout.alert_change, null);
                AlertDialog.Builder change_dialog = new AlertDialog.Builder(MainActivity.this);
                change_dialog.setCancelable(true);
                change_dialog.setView(chan);
                change_dialog.create();
                final AlertDialog sh = change_dialog.show();
                final EditText old = (EditText) chan.findViewById(R.id.old_pass);
                final EditText new_pass = (EditText) chan.findViewById(R.id.new_pass);
                final EditText hint_new = (EditText) chan.findViewById(R.id.new_hint);
                Button done = (Button) chan.findViewById(R.id.new_done);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String old_pass = old.getText().toString();
                        String new_password = new_pass.getText().toString();
                        String new_hint = hint_new.getText().toString();
                        if (change.getString(APP_PASSWORD, null).equals(old_pass)){
                            if (new_password.equals("")|new_password.equals(" ")){
                                Toast.makeText(getApplicationContext(), "Кажется, Вы забыли ввести пароль!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                SharedPreferences.Editor q = change.edit();
                                q.putString(APP_PASSWORD, new_password);
                                q.putString(APP_HINT, new_hint);
                                q.apply();
                                q.commit();
                                Toast.makeText(getApplicationContext(), "Пароль успешно изменен!", Toast.LENGTH_SHORT).show();
                                sh.dismiss();
                            }
                        } else Toast.makeText(getApplicationContext(), "Старый и новый пароли не совпадают!", Toast.LENGTH_SHORT).show();
                    }
                });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor sharedPreferences = getPreferences(MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(postData);
        sharedPreferences.putString("postData", json);
        sharedPreferences.commit();
        sharedPreferences.apply();

        SharedPreferences.Editor tab_ed = mTab.edit();
        tab_ed.putInt(TAB_NUMBER, tab_launch);
        tab_ed.commit();
        tab_ed.apply();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if (del_key == true) {
            postData.clear();
            ratesdays.clear();
            datedays.clear();

            SharedPreferences.Editor a = getPreferences(MODE_PRIVATE).edit();
            a.remove("postData");
            a.commit();
            a.apply();

            del_key = false;

            SharedPreferences.Editor edit = delete_m.edit();
            edit.putBoolean(BINARY_DELETE_KEY, del_key);
            edit.commit();
            edit.apply();

            itemAdapter.notifyDataSetChanged();
        }
    }
    public void reload(){
        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        } else {
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);

            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }
}

