package com.example.weddingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;


import com.example.weddingapp.databinding.ActivityChooseWeddingPlaceBinding;
import com.example.weddingapp.databinding.ActivityPlaceBinding;

import java.io.IOException;
import java.util.ArrayList;


public class PlaceActivity extends AppCompatActivity {

    ActivityPlaceBinding binding;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button button = findViewById(R.id.btn1);

        // Get an instance of SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Get an instance of SharedPreferences.Editor
        SharedPreferences.Editor editor = preferences.edit();


        Intent intent = this.getIntent();

        if (intent != null){

            int imageid = intent.getIntExtra("imageid",R.drawable.wp1);
            int capacity = intent.getIntExtra("capacity",0);
            name = intent.getStringExtra("name");
            String city = intent.getStringExtra("city");
            String alcohol = intent.getStringExtra("alcohol");
            float rating = intent.getFloatExtra("rating",0);


            binding.img1.setImageResource(imageid);
            binding.tv1.setText("Capacity: " + capacity);
            binding.tv2.setText("Name: " + name);
            binding.tv3.setText("City: " + city);
            binding.tv4.setText("Alcohol: " + alcohol);
            binding.ratingBar.setRating(rating);


        }

        int REQUEST_CODE = 0;
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermissions(permissions, REQUEST_CODE);



        final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.notificationhappy);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.start();

                editor.putString("weddingplacename", name);
                editor.commit();

                makeAndShowDialogBox("Set as Wedding Place");





            }
        });
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