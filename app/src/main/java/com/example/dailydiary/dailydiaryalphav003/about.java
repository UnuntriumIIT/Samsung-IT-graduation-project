package com.example.dailydiary.dailydiaryalphav003;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class about extends AppCompatActivity {
    private ArrayList<String> linksList = new ArrayList<>();
    private ArrayList<String> thanks = new ArrayList<>();
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ActionBar toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);
        setTitle("О разработчике");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        linksList.add(0,"Вконтакте");
        linksList.add(1,"Email");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, linksList);
        ListView links = (ListView) findViewById(R.id.links);
        links.setAdapter(adapter);
        links.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0 :
                        Intent link_vk = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://vk.com/id145650458"));
                        startActivity(link_vk);
                        break;
                    case 1 :
                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("", "andreidmarket@mail.ru");
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(context, "Мой адрес эл. почты скопирован в буфер обмена.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ListView thank = (ListView) findViewById(R.id.thanks);
        thanks.add(0, "Деникин Антон Витальевич");
        thanks.add(1, "Пичугин Иван Андреевич");
        thanks.add(2, "Погорелов Кирилл Сергеевич");
        ArrayAdapter<String> adap = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, thanks);
        thank.setAdapter(adap);

        thank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Toast.makeText(getApplicationContext(), "Преподаватель, много помогал и всему научил :)", Toast.LENGTH_LONG).show(); break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "Друг, одноклассник, помог по программной части", Toast.LENGTH_LONG).show(); break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "Друг, одноклассник, ответственный за дизайн этого приложения", Toast.LENGTH_LONG).show(); break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
