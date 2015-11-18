package com.example.will.slugsports;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import java.util.Calendar;

public class createEvent extends FragmentActivity
        implements TimePickerFragment.OnTimeSelectedListener{

    Bundle bundle;
    ParseObject event;
    int hour;
    int minute;
    String AM_PM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        bundle = getIntent().getExtras();
/*
        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.will", Context.MODE_PRIVATE);

        if(prefs.getBoolean("firstCreateEvent", true)) {

            Parse.enableLocalDatastore(this);

            //Parse.initialize(this, "7st18qTMNhNJICNJx1hY5cbk8BzSKB99fKx1qCgP", "zeyvANSw3bh0yLOiPtQJ05052qaKFNIaV7cP83Og");

            prefs.edit().putBoolean("firstCreateEvent", false).apply();
        }
*/

/*
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "7st18qTMNhNJICNJx1hY5cbk8BzSKB99fKx1qCgP", "zeyvANSw3bh0yLOiPtQJ05052qaKFNIaV7cP83Og");
*/
        event = new ParseObject("Event");

        EditText editText = (EditText) findViewById(R.id.editText10);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }


        });
        //prevent keyboard
        editText.setRawInputType(InputType.TYPE_NULL);
        editText.setFocusable(true);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    public void buttonPressed(View v){

        EditText editText = (EditText) findViewById(R.id.editText4);
        String userName = editText.getText().toString();

        //Add error checking/filling in default values
        if(userName.length() < 1){
            Toast.makeText(getApplicationContext(), "Please enter a name", Toast.LENGTH_LONG).show();
            ;
        }

        if(AM_PM.length() < 1){
            Toast.makeText(getApplicationContext(), "Please enter a time", Toast.LENGTH_LONG).show();

        }

        editText = (EditText) findViewById(R.id.editText);
        String eventName = editText.getText().toString();

        if(eventName.length() < 1){
            Toast.makeText(getApplicationContext(), "Please enter an event name", Toast.LENGTH_LONG).show();

        }

        editText = (EditText) findViewById(R.id.editText2);
        String numPlayers = editText.getText().toString();

        if(numPlayers.length() < 1){
            Toast.makeText(getApplicationContext(), "Please specify the number of players", Toast.LENGTH_LONG).show();

        }

        editText = (EditText) findViewById(R.id.editText3);
        String eventDescription = editText.getText().toString();

        if(eventDescription.length() < 1){
            Toast.makeText(getApplicationContext(), "Please enter a description", Toast.LENGTH_LONG).show();

        }

        int day = bundle.getInt("day");
        int month = bundle.getInt("month");
        int year = bundle.getInt("year");


        event.put("userName", userName);
        event.put("eventName", eventName);
        event.put("numPlayers", numPlayers);
        event.put("description", eventDescription);
        event.put("day", day);
        event.put("month", month);
        event.put("year", year);

        event.put("minute", minute);
        event.put("hour", hour);
        event.put("AM_PM", AM_PM);


        Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
        Log.i("DEBUG", "Saving in BG");
    //    event.saveInBackground();
        event.saveInBackground();
        Log.i("DEBUG", "Saved in BG");

    }

    public void onTimeSelected(int hourOfEvent, int minuteOfEvent){
        minute = minuteOfEvent;

        if(hourOfEvent > 12){
            hour = hourOfEvent % 12;
            AM_PM = "PM";
        }

        else {
            hour = hourOfEvent;
            AM_PM = "AM";
        }

        EditText editText= (EditText) findViewById(R.id.editText10);
        if(minute > 10)
            editText.setText(hour + ":" + minute + " " + AM_PM);
        else
            editText.setText(hour + ":0" + minute + " " + AM_PM);
    }
}
