package com.example.dailydiary.dailydiaryalphav003;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class postItemAdapter extends BaseAdapter {

    Context context;
    private ArrayList<PostData> postData;
    LayoutInflater inflater;

    public postItemAdapter(Context context, ArrayList<PostData> postData) {
        this.context = context;
        this.postData = postData;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return postData.size();
    }

    @Override
    public Object getItem(int i) {
        return postData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.listview_item, parent, false);
        }

        View rowView = inflater.inflate(R.layout.listview_item, null);

        ImageView day_nastr = (ImageView) rowView.findViewById(R.id.img_day);

        switch (postData.get(position).what_img){
            case "0": day_nastr.setImageResource(R.drawable.baad); break;
            case "1": day_nastr.setImageResource(R.drawable.disappointed_face); break;
            case "2": day_nastr.setImageResource(R.drawable.confused_face); break;
            case "3": day_nastr.setImageResource(R.drawable.neutral_face); break;
            case "4": day_nastr.setImageResource(R.drawable.slightly_smiling_face); break;
            case "5": day_nastr.setImageResource(R.drawable.smiling_face); break;
        }

        TextView title = (TextView)rowView.findViewById(R.id.main_title);
        title.setText(postData.get(position).postTitle);

        TextView desc = (TextView)rowView.findViewById(R.id.main_description);
        desc.setText(postData.get(position).postDesc);

        TextView day = (TextView)rowView.findViewById(R.id.app_day_date_main);

        day.setText(postData.get(position).day_result);

        TextView date = (TextView)rowView.findViewById(R.id.date_main);

        date.setText(postData.get(position).date_main);

        return rowView;
    }
}

