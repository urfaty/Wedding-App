package com.example.weddingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CalculateBudgetActivity extends AppCompatActivity {



    private EditText et1;
    private EditText et2;
    private EditText et3;
    private EditText et4;
    private EditText et5;
    private EditText et6;
    private EditText et7;
    private EditText et8;
    private EditText et9;

    private TextView tv11;
    private String totalCost;
    private int totalCostInteger;

    boolean allEditTextsEmpty = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_budget);

        LinearLayout layout = findViewById(R.id.layout1);


        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        et5 = (EditText) findViewById(R.id.et5);
        et6 = (EditText) findViewById(R.id.et6);
        et7 = (EditText) findViewById(R.id.et7);
        et8 = (EditText) findViewById(R.id.et8);
        et9 = (EditText) findViewById(R.id.et9);
        tv11 = (TextView) findViewById(R.id.tv11);


        // Get an instance of SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Get an instance of SharedPreferences.Editor
        SharedPreferences.Editor editor = preferences.edit();

        String value1 = preferences.getString("etkey1", "");
        String value2 = preferences.getString("etkey2", "");
        String value3 = preferences.getString("etkey3", "");
        String value4 = preferences.getString("etkey4", "");
        String value5 = preferences.getString("etkey5", "");
        String value6 = preferences.getString("etkey6", "");
        String value7 = preferences.getString("etkey7", "");
        String value8 = preferences.getString("etkey8", "");
        String value9 = preferences.getString("etkey9", "");
        String value11 = preferences.getString("tvkey11", "");


        et1.setText(value1);
        et2.setText(value2);
        et3.setText(value3);
        et4.setText(value4);
        et5.setText(value5);
        et6.setText(value6);
        et7.setText(value7);
        et8.setText(value8);
        et9.setText(value9);
        tv11.setText(value11);




        Button button1 = findViewById(R.id.btn1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (et1.getText().length() == 0) {
                     allEditTextsEmpty = true;
                } else if (et2.getText().length() == 0) {
                    allEditTextsEmpty = true;
                } else if (et3.getText().length() == 0) {
                    allEditTextsEmpty = true;
                } else if (et4.getText().length() == 0) {
                    allEditTextsEmpty = true;
                } else if (et5.getText().length() == 0) {
                    allEditTextsEmpty = true;
                } else if (et6.getText().length() == 0) {
                    allEditTextsEmpty = true;
                } else if (et7.getText().length() == 0) {
                    allEditTextsEmpty = true;
                } else if (et8.getText().length() == 0) {
                    allEditTextsEmpty = true;
                } else if (et9.getText().length() == 0) {
                    allEditTextsEmpty = true;
                } else {
                    allEditTextsEmpty = false;
                }




                if (allEditTextsEmpty) {
                    displayToast("You can't leave empty area");
                    final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.upset);
                    mediaPlayer.start();
                } else {
                    final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.notificationhappy);
                    mediaPlayer.start();
                    totalCostInteger = Integer.parseInt(et1.getText().toString().trim())+
                            Integer.parseInt(et2.getText().toString().trim())+
                            Integer.parseInt(et3.getText().toString().trim())+
                            Integer.parseInt(et4.getText().toString().trim())+
                            Integer.parseInt(et5.getText().toString().trim())+
                            Integer.parseInt(et6.getText().toString().trim())+
                            Integer.parseInt(et7.getText().toString().trim())+
                            Integer.parseInt(et8.getText().toString().trim())+
                            Integer.parseInt(et9.getText().toString().trim());

                    totalCost = String.valueOf(totalCostInteger);
                    tv11.setText(totalCost);

                    // Add a key-value pair
                    editor.putString("etkey1", et1.getText().toString());
                    editor.putString("etkey2", et2.getText().toString());
                    editor.putString("etkey3", et3.getText().toString());
                    editor.putString("etkey4", et4.getText().toString());
                    editor.putString("etkey5", et5.getText().toString());
                    editor.putString("etkey6", et6.getText().toString());
                    editor.putString("etkey7", et7.getText().toString());
                    editor.putString("etkey8", et8.getText().toString());
                    editor.putString("etkey9", et9.getText().toString());
                    editor.putString("tvkey11", totalCost);

                    // Save the changes
                    editor.commit();
                }

            }
        });


        Button button2 = findViewById(R.id.btn2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read a value from Shared Preferences

                final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.notificationhappy);
                mediaPlayer.start();

                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
                et5.setText("");
                et6.setText("");
                et7.setText("");
                et8.setText("");
                et9.setText("");
                tv11.setText("");




                editor.remove("etkey1");
                editor.remove("etkey2");
                editor.remove("etkey3");
                editor.remove("etkey4");
                editor.remove("etkey5");
                editor.remove("etkey6");
                editor.remove("etkey7");
                editor.remove("etkey8");
                editor.remove("etkey9");
                editor.remove("tvkey11");


                editor.commit();

            }
        });







    }









    private void displayToast(String msg) {

        Toast toast = Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT);
        // Appears in the center
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        // Appears in the top-left corner
        // toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
        toast.show();

        // To use Toast in one go
        // Appear in the center bottom by default
        // Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

}