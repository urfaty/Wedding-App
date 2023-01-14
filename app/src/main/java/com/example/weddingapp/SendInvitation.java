package com.example.weddingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.os.Bundle;

public class SendInvitation extends AppCompatActivity {
    private EditText mEditTextTo;
    private EditText mEditTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_invitation);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        mEditTextTo = findViewById(R.id.edit_text_to);
        mEditTextMessage = findViewById(R.id.edit_text_message);

        Button buttonSend = findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });


        String place = preferences.getString("weddingplacename", "Place");
        String date = preferences.getString("currenttimetext", "date");
        int hour = preferences.getInt("selectedhour", 17);
        int minute = preferences.getInt("minute", 30);


        mEditTextMessage.setText("The pleasure of your company" +
                                    " is requested. To celebrate the" +
                                    " our marriage\n\n\n" +
                "Place: "+ place+"\n" +
                "Date: "+date+"\n" +
                "Time: "+ hour+"."+ minute
        );

    }

    private void sendMail() {
        String recipientList = mEditTextTo.getText().toString();
        String[] recipients = recipientList.split(",");

        String message = mEditTextMessage.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Wedding Invitation");
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }
}