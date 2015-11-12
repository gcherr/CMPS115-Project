package com.example.will.slugsports;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

public class createEvent extends AppCompatActivity {

    Bundle bundle;
    ParseObject event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        bundle = getIntent().getExtras();

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "7st18qTMNhNJICNJx1hY5cbk8BzSKB99fKx1qCgP", "zeyvANSw3bh0yLOiPtQJ05052qaKFNIaV7cP83Og");

        event = new ParseObject("Event");

    }

    public void buttonPressed(View v){




        EditText editText = (EditText) findViewById(R.id.editText4);
        String userName = editText.getText().toString();

        //Add error checking/filling in default values
        if(userName == null){

        }

        editText = (EditText) findViewById(R.id.editText);
        String eventName = editText.getText().toString();

        editText = (EditText) findViewById(R.id.editText2);
        String numPlayers = editText.getText().toString();

        editText = (EditText) findViewById(R.id.editText3);
        String eventDescription = editText.getText().toString();

        int day = bundle.getInt("day");
        int month = bundle.getInt("month");
        int year = bundle.getInt("year");

        int minute = bundle.getInt("minute");
        int hour = bundle.getInt("hour");
        boolean AM = bundle.getBoolean("AM?");

        event.put("userName", userName);
        event.put("eventName", eventName);
        event.put("numPlayers", numPlayers);
        event.put("description", eventDescription);
        event.put("day", day);
        event.put("month", month);
        event.put("year", year);
        event.put("minute", minute);
        event.put("hour", hour);
       // event.put("AM?", AM);

        Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();

    //    event.saveInBackground();
        event.saveInBackground();

    }
}
