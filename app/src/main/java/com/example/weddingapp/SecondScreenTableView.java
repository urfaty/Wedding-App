package com.example.weddingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SecondScreenTableView extends AppCompatActivity {

    private String[] currencies = {"Calculate the Budget", "Choose Wedding Place", "Time Left to Wedding", "Send Invitation"};
    private ArrayAdapter<String> mArrayAdapter;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen_table_view);

        mListView = (ListView) findViewById(R.id.list1);

        mArrayAdapter = new CustomAdapter(this, currencies);
        mListView.setAdapter(mArrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView tv = (TextView) view.findViewById(R.id.label);

                switch (position) {
                    case 0:
                        Intent intent0 = new Intent(SecondScreenTableView.this, CalculateBudgetActivity.class);
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1 = new Intent(SecondScreenTableView.this, ChooseWeddingPlaceActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(SecondScreenTableView.this, TimerCountDownActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(SecondScreenTableView.this, SendInvitation.class);
                        startActivity(intent3);
                        break;

                }
            }
        });


    }
}