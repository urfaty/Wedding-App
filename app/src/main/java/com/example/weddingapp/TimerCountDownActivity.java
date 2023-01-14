package com.example.weddingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Locale;


import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import android.app.TimePickerDialog;
import android.widget.TimePicker;


public class TimerCountDownActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final long START_TIME_IN_MILLIS = 9000;

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    Button timeButton;
    int hour, minute;
    private int selectedHours;
    private int selectedMinutes;
    private int selectedDays;
    private int selectedYears;
    private int selectedMonths;
    private Boolean isHourAndMinuteSelected = false;
    private Boolean isDaySelected = false;
    private CountDownTimer mCountDownTimer;
    private Boolean isCountDownStartPressed = false;
    private boolean mTimerRunning;
    private boolean isValueEntered = false;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_count_down);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        timeButton = findViewById(R.id.timeButton);


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((isDaySelected && isHourAndMinuteSelected) || isValueEntered) {

                    if (mTimerRunning) {
                        pauseTimer();
                    } else {
                        setContentOfTimer();
                        startTimer();
                    }
                } else {
                    final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.upset);
                    mediaPlayer.start();
                    makeAndShowDialogBox("Please select day and time first");

                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putBoolean("isValueEntered",false);
                editor.commit();
                isValueEntered = false;
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText("");

                TextView textViewtop = (TextView) findViewById(R.id.textViewtop);
                textViewtop.setText("");


                resetTimer();
            }
        });


        String timeLeftFormatted1 = String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d", 0, 0, 0, 0);
        mTextViewCountDown.setText(timeLeftFormatted1);


        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        //SharedPreferences.Editor editor = preferences.edit();
        isValueEntered = preferences.getBoolean("isValueEntered",false);

        if(isValueEntered) {
            isValueEntered();
            startTimer();
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(preferences.getString("currenttimetext","Date"));
            TextView textViewtop = (TextView) findViewById(R.id.textViewtop);
            textViewtop.setText("Wedding Day");
        }
    }





















    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = 0;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        long days = (mTimeLeftInMillis / 1000) / 86400;
        long hours = ((mTimeLeftInMillis / 1000) / 3600) % 24;
        long minutes = ((mTimeLeftInMillis / 1000) / 60) % 60;
        long seconds = (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted1 = String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted1);



    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar weddingDay = Calendar.getInstance();
        weddingDay.set(Calendar.YEAR, year);
        weddingDay.set(Calendar.MONTH, month);
        weddingDay.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        weddingDay.set(Calendar.HOUR_OF_DAY, 0);
        weddingDay.set(Calendar.MINUTE, 0);
        weddingDay.set(Calendar.SECOND, 0);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(weddingDay.getTime());

        Calendar currentTimeCalendar = Calendar.getInstance();
        long currentTimeInMillis = currentTimeCalendar.getTimeInMillis();
        long differenceInMillis = weddingDay.getTimeInMillis() - currentTimeInMillis;
        long differenceInDays = TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS);

        if (differenceInDays < 0) {
            makeAndShowDialogBox("Please don't select any date from the past");


        } else {


            selectedDays = dayOfMonth;
            selectedYears = year;
            selectedMonths = month;

            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("dayofmonth", dayOfMonth);
            editor.putInt("year", year);
            editor.putInt("month", month);
            editor.putString("currenttimetext",currentDateString);
            editor.commit();



            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(currentDateString);
            TextView textViewtop = (TextView) findViewById(R.id.textViewtop);
            textViewtop.setText("Wedding Day");
            isDaySelected = true;

        }


    }


    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;

                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("selectedhour", selectedHour);
                editor.putInt("minute", selectedMinute);
                editor.commit();



                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));

                TimerCountDownActivity.this.selectedHours = selectedHour;
                selectedMinutes = selectedMinute;
                isHourAndMinuteSelected = true;

            }
        };

        // int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }


    private void setContentOfTimer() {

        if (isDaySelected && isHourAndMinuteSelected) {

            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putBoolean("isValueEntered",true);
            editor.commit();

            int selectedhour = preferences.getInt("selectedhour",23);
            int minute = preferences.getInt("minute",60);
            int dayofmonth = preferences.getInt("dayofmonth",29);
            int year = preferences.getInt("year",2022);
            int month = preferences.getInt("month",12);

            Calendar weddingDay = Calendar.getInstance();
            weddingDay.set(Calendar.YEAR, year);
            weddingDay.set(Calendar.MONTH, month);
            weddingDay.set(Calendar.DAY_OF_MONTH, dayofmonth);
            weddingDay.set(Calendar.HOUR_OF_DAY, selectedhour);
            weddingDay.set(Calendar.MINUTE, minute);
            weddingDay.set(Calendar.SECOND, 0);


            Calendar currentTimeCalendar = Calendar.getInstance();
            long currentTimeInMillis = currentTimeCalendar.getTimeInMillis();
            long differenceInMillis = weddingDay.getTimeInMillis() - currentTimeInMillis;

            mTimeLeftInMillis = differenceInMillis;
            updateCountDownText();

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

    private void isValueEntered() {

        if (isValueEntered) {

            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            //SharedPreferences.Editor editor = preferences.edit();

            int selectedhour = preferences.getInt("selectedhour",23);
            int minute = preferences.getInt("minute",60);
            int dayofmonth = preferences.getInt("dayofmonth",29);
            int year = preferences.getInt("year",2022);
            int month = preferences.getInt("month",12);

            Calendar weddingDay = Calendar.getInstance();
            weddingDay.set(Calendar.YEAR, year);
            weddingDay.set(Calendar.MONTH, month);
            weddingDay.set(Calendar.DAY_OF_MONTH, dayofmonth);
            weddingDay.set(Calendar.HOUR_OF_DAY, selectedhour);
            weddingDay.set(Calendar.MINUTE, minute);
            weddingDay.set(Calendar.SECOND, 0);


            Calendar currentTimeCalendar = Calendar.getInstance();
            long currentTimeInMillis = currentTimeCalendar.getTimeInMillis();
            long differenceInMillis = weddingDay.getTimeInMillis() - currentTimeInMillis;

            mTimeLeftInMillis = differenceInMillis;
            updateCountDownText();

        }


    }

}