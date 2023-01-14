package com.example.weddingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.app.AlertDialog;
import android.widget.TextView;
import com.example.weddingapp.databinding.ActivityChooseWeddingPlaceBinding;
import com.google.android.material.slider.Slider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ChooseWeddingPlaceActivity extends AppCompatActivity {

    ActivityChooseWeddingPlaceBinding binding;
    private Spinner spinner1;
    private Slider slider1;
    private String alcoholIncluded = "empty";
    private String selectedCity = "";
    private int selecteedCapacity = 0;
    private float selectedRating = 1f; // Sonradan

    ArrayList<Integer> capacity1 = new ArrayList<>();
    ArrayList<String> name1 = new ArrayList<>();
    ArrayList<String> city1 = new ArrayList<>();
    ArrayList<String> alcohol1 = new ArrayList<>();
    ArrayList<Float> ratings1 = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_wedding_place);


        binding = ActivityChooseWeddingPlaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        spinner1 = (Spinner) findViewById(R.id.spin1);
        slider1 = (Slider) findViewById(R.id.slider1);
        RatingBar ratingBar = findViewById(R.id.rating_bar); // Sonradan


        InputStream inputStream = getResources().openRawResource(R.raw.places);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("Text Data", byteArrayOutputStream.toString());
        try {
            // Parse the data into jsonobject to get original data in form of json.
            JSONObject jObject = new JSONObject(
                    byteArrayOutputStream.toString());
            JSONObject jObjectResult = jObject.getJSONObject("Places");
            JSONArray jArray = jObjectResult.getJSONArray("Place");
            int capacity = 0;
            String name = "";
            String city = "";
            String alcohol = "";
            int ratings = 1;
            //String cat_name = "";
            // ArrayList<String[]> data = new ArrayList<String[]>();
            for (int i = 0; i < jArray.length(); i++) {
                //cat_name = jArray.getJSONObject(i).getString("cat_name");

                capacity = jArray.getJSONObject(i).getInt("capacity");
                name = jArray.getJSONObject(i).getString("name");
                city = jArray.getJSONObject(i).getString("city");
                alcohol = jArray.getJSONObject(i).getString("alcohol");
                ratings = jArray.getJSONObject(i).getInt("ratings");


                capacity1.add(capacity);
                name1.add(name);
                city1.add(city);
                alcohol1.add(alcohol);
                ratings1.add((float)ratings);


                // data.add(new String[] { cat_Id, cat_name });
            }



        } catch (Exception e) {
            e.printStackTrace();
        }



        Integer[] capacityArray = capacity1.toArray(new Integer[0]);
        String[] nameArray = name1.toArray(new String[0]);
        String[] cityArray = city1.toArray(new String[0]);
        String[] alcoholArray = alcohol1.toArray(new String[0]);
        Float[] ratingsArray = ratings1.toArray(new Float[0]);





















        int[] imageId = {R.drawable.wp1, R.drawable.wp2, R.drawable.wp3, R.drawable.wp4, R.drawable.wp5,
                R.drawable.wp6, R.drawable.wp7, R.drawable.wp8, R.drawable.wp9, R.drawable.wp1, R.drawable.wp2, R.drawable.wp3, R.drawable.wp4
                , R.drawable.wp5, R.drawable.wp6, R.drawable.wp7, R.drawable.wp8, R.drawable.wp9, R.drawable.wp1
                , R.drawable.wp2, R.drawable.wp3, R.drawable.wp4};

        ArrayList<Place> placeArrayList = new ArrayList<>();
        ArrayList<Place> removedItems = new ArrayList<>();
        ArrayList<Place> allItemsCopy = new ArrayList<>();
        ArrayList<Place> itemsWillBeShown = new ArrayList<>();



        for (int i = 0; i < imageId.length; i++) {

            Place place = new Place(capacityArray[i], nameArray[i], cityArray[i], alcoholArray[i], imageId[i], ratingsArray[i]);
            placeArrayList.add(place);

        }

        CustomAdapterChoosePlace listAdapter = new CustomAdapterChoosePlace(ChooseWeddingPlaceActivity.this, placeArrayList);

        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listAdapter.addAll(removedItems);
                //removedItems.clear();
                //listAdapter.notifyDataSetChanged();

                Place item = (Place) parent.getItemAtPosition(position);

                Intent i = new Intent(ChooseWeddingPlaceActivity.this, PlaceActivity.class);
                i.putExtra("capacity", item.capacity);
                i.putExtra("name", item.name);
                i.putExtra("city", item.city);
                i.putExtra("alcohol", item.alcohol);
                i.putExtra("rating", item.rating);
                i.putExtra("imageid", item.imageId);

                startActivity(i);


            }
        });


        int count = listAdapter.getCount();
        for (int i = 0; i < count; i++) {
            // Get the item at the current position
            Place item = listAdapter.getItem(count-1-i);
            //Place item = listAdapter.getItem(i);

            removedItems.add(item);
            allItemsCopy.add(item);
            listAdapter.remove(item);

        }
        //listAdapter.clear();
        //listAdapter.notifyDataSetChanged();



        Button button = findViewById(R.id.btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                selectedRating = ratingBar.getRating(); // sonradan
                selectedCity = spinner1.getSelectedItem().toString();
                selecteedCapacity = (int) slider1.getValue();
                listAdapter.clear();
                listAdapter.notifyDataSetChanged();

                if (alcoholIncluded.equals("empty")) {
                    makeAndShowDialogBox("Please Fill All Empty Areas");
                    final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.upset);
                    mediaPlayer.start();

                } else {

                    final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.notificationhappy);
                    mediaPlayer.start();

                    if (alcoholIncluded.equals("Both")) {
                        for (Place element : allItemsCopy) {

                            if (element.capacity >= selecteedCapacity && element.city.equals(selectedCity) && element.rating >= selectedRating) { // Sonradan
                                listAdapter.add(element);
                                listAdapter.notifyDataSetChanged();
                            }
                        }

                    } else {
                        for (Place element : allItemsCopy) {

                            if (element.alcohol.equals(alcoholIncluded) && element.capacity >= selecteedCapacity && element.city.equals(selectedCity) && element.rating >= selectedRating) { // sonradan
                                listAdapter.add(element);
                                listAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                }


            }
        });


    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rdb1:
                alcoholIncluded = "Yes";
                break;
            case R.id.rdb2:
                alcoholIncluded = "No";
                break;
            case R.id.rdb3:
                alcoholIncluded = "Both";
                break;
        }
    }


    private void makeAndShowDialogBox(String message) {

        AlertDialog.Builder myDialogBox = new AlertDialog.Builder(this);

        myDialogBox.setTitle("AlertDialog");
        myDialogBox.setMessage(message);

        myDialogBox.setPositiveButton("Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // whatever should be done when answering "YES" goes
                        // here

                    }
                });

        myDialogBox.create();
        myDialogBox.show();
    }


}

