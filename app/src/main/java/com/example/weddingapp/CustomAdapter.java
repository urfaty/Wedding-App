package com.example.weddingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] values;
    private LayoutInflater inflater;

    public CustomAdapter(Context context, String[] values) {
        super(context, R.layout.list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item, parent, false);
        }

        //ImageView mImageView = (ImageView) rowView.findViewById(R.id.logo);
        TextView mTextView = (TextView) rowView.findViewById(R.id.label);


        mTextView.setText(values[position]);

        // Change icon based on name
        String s = values[position];

        return rowView;
    }
}