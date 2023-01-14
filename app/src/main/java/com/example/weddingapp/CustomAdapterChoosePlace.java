package com.example.weddingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapterChoosePlace extends ArrayAdapter<Place> {


    public CustomAdapterChoosePlace(Context context, ArrayList<Place> placeArrayList){

        super(context,R.layout.list_item_choose_place, placeArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Place place = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_choose_place,parent,false);

        }

        ImageView imageView = convertView.findViewById(R.id.img1);
        TextView capacity = convertView.findViewById(R.id.tv5);
        TextView name = convertView.findViewById(R.id.tv6);
        TextView city = convertView.findViewById(R.id.tv7);
        TextView alcohol = convertView.findViewById(R.id.tv8);
        RatingBar ratingBar = convertView.findViewById(R.id.rating_bar); // Sonradan


        imageView.setImageResource(place.imageId);
        capacity.setText(String.valueOf(place.capacity));
        name.setText(place.name);
        city.setText(place.city);
        alcohol.setText(place.alcohol);
        ratingBar.setRating(place.rating);



        return convertView;
    }
}