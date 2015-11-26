package com.example.will.slugsports;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class FoundGames extends AppCompatActivity {

    private ArrayAdapter<String> arrayAdapter;

    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //
        extras = getIntent().getExtras();

        //
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_found_games);

        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.will", Context.MODE_PRIVATE);

        prefs.edit().putBoolean("firstCreateEvent", true).apply();


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.whereEqualTo("sport", extras.getString("sport"));
        query.whereEqualTo("location", extras.getString("location"));
        Log.i("DEBUG", extras.getString("location"));
        query.whereEqualTo("day", extras.getInt("day"));
        query.whereEqualTo("month", extras.getInt("month"));
        query.whereEqualTo("year", extras.getInt("year"));

        //final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>{};
        ArrayList<String> arrayList = new ArrayList<String>();

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> eventList, ParseException e) {
                if (e == null && eventList.size() > 0) {
                    Log.i("DEBUG", "Retrieved " + eventList.size() + " event. Event name: " + eventList.get(0).getString("eventName"));
                    CharSequence c = "Retrieved " + eventList.size() + " event. Event name: " + eventList.get(0).getString("eventName");
                    Toast.makeText(FoundGames.this, c, Toast.LENGTH_SHORT).show();
                    ListView myListView = (ListView) findViewById(R.id.listView2);

                    final ArrayList<String> aList = new ArrayList<String>();

                    for(int i = 0; i < eventList.size(); i++){
                        Object object = eventList.get(i);
                        //String name = ((ParseObject) object).getObjectId().toString();
                        String name = ((ParseObject) object).getString("eventName");
                        aList.add(name);
                    }

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            FoundGames.this, android.R.layout.simple_list_item_1, aList
                    );

                    myListView.setAdapter(arrayAdapter);

                } else {
                    Log.i("DEBUG", "No Events on day");
                    //MAKE TOAST;
                }
            }
        });







        /*
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        /*


        if (extras == null || extras.size() == 0) {

        } else {
            ListView myListView = (ListView) findViewById(R.id.listView);
            myListView.setAdapter(arrayAdapter);
        }
        */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void createEvent(View v){

        Intent intent = new Intent(FoundGames.this, createEvent.class);

        intent.putExtras(extras);

        startActivity(intent);
    }

}
