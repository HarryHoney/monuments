package com.example.harpreet.visitorguide.UtilsFolder;


import android.app.Activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.harpreet.visitorguide.R;

import org.json.JSONArray;

import java.util.List;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> maintitle;
    private final List<String> subtitle;

    public MyListAdapter(Activity context, List<String> maintitle, List<String> subtitle) {
        super(context, R.layout.place_list_items, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.place_list_items, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);
        Log.d("values",maintitle.get(position));
        titleText.setText(maintitle.get(position));
        subtitleText.setText(subtitle.get(position)+" Visits");

        return rowView;

    };
}