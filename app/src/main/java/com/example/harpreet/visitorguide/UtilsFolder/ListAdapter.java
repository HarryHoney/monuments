package com.example.harpreet.visitorguide.UtilsFolder;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.harpreet.visitorguide.R;

public class ListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] title;
    private final String[] dist;
    public ListAdapter(Activity context, String[] maintitle, String[] subtitle) {
        super(context, R.layout.place_list_item, maintitle);
        this.context=context;
        this.title=maintitle;
        this.dist=subtitle;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.place_list_item, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        titleText.setText(title[position]);
        subtitleText.setText(dist[position]);
        return rowView;

    };
}