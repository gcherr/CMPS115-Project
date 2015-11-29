package com.example.will.slugsports;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FoundGames extends AppCompatActivity {

    private ArrayAdapter<String> arrayAdapter;
    ProgressDialog progressDialog;
    Bundle extras;
    final ArrayList<String> arrayList = new ArrayList<String>();
    ListView myListView;

    List<Map<String, String>> list = new ArrayList<Map<String, String>>();


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

        //display criteria on bottom
        TextView temp = (TextView) findViewById(R.id.textView);
        temp.setText("Events for " +
                extras.getString("sport") + " at " + extras.getString("location") + " on " +
                extras.getInt("month") + "/" + extras.getInt("day") + "/" +
                extras.getInt("year"));

        myListView = (ListView) findViewById(R.id.listView2);
        //set the listener for list elements and sends the data
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(FoundGames.this, idList.get(position), Toast.LENGTH_SHORT).show();
                Toast.makeText(FoundGames.this, list.get(position).get("id"), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(FoundGames.this, ViewEvent.class);
                startActivity(intent);
            }
        });

        //fetchParse();

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

    @Override
    public void onResume(){
        super.onResume();

        //clear the list so events don't accumulate
        arrayList.clear();
        myListView = (ListView) findViewById(R.id.listView2);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                FoundGames.this, android.R.layout.simple_list_item_1, arrayList
        );
        myListView.setAdapter(arrayAdapter);

        fetchParse();
    }

    public void createEvent(View v){

        Intent intent = new Intent(FoundGames.this, createEvent.class);

        intent.putExtras(extras);

        startActivity(intent);
    }

    public void fetchParse(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.whereEqualTo("sport", extras.getString("sport"));
        query.whereEqualTo("location", extras.getString("location"));
        Log.i("DEBUG", extras.getString("location"));
        query.whereEqualTo("day", extras.getInt("day"));
        query.whereEqualTo("month", extras.getInt("month"));
        query.whereEqualTo("year", extras.getInt("year"));

        progressDialog = ProgressDialog.show(FoundGames.this, "",
                "Fetching available games...", true);

        //final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>{};
        //final ArrayList<String> arrayList = new ArrayList<String>();

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> eventList, ParseException e) {
                if (e == null && eventList.size() > 0) {
                    Log.i("DEBUG", "Retrieved " + eventList.size() + " event. Event name: " +
                            eventList.get(0).getString("eventName"));
                    CharSequence c = "Retrieved " + eventList.size() + " event(s).";
                    Toast.makeText(FoundGames.this, c, Toast.LENGTH_SHORT).show();
                    //myListView = (ListView) findViewById(R.id.listView2);

                    //final ArrayList<String> aList = new ArrayList<String>();
                    //final ArrayList<String> rrayList = arrayList;

                    //List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                    Map<String, String> map;

                    for(int i = 0; i < eventList.size(); i++){
                        map = new HashMap<String, String>();
                        Object object = eventList.get(i);
                        //String name = ((ParseObject) object).getObjectId().toString();
                        //String name = ((ParseObject) object).getString("eventName");
                        //arrayList.add(name);

                        String hourString = ((ParseObject) object).getInt("hour")+"";
                        int minute = ((ParseObject) object).getInt("minute");
                        String minuteString = (minute < 10) ? "0"+minute : ""+minute;

                        map.put("eventName", ((ParseObject) object).getString("eventName"));
                        map.put("id", ((ParseObject) object).getObjectId());
                        map.put("username", ((ParseObject) object).getString("userName"));
                        map.put("time", (hourString + ":" + minuteString + " " +
                                ((ParseObject) object).getString("AM_PM")));
                        list.add(map);
                    }
/*
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            FoundGames.this, android.R.layout.simple_list_item_1, arrayList
                    );
*/
                    SimpleAdapter adapter = new SimpleAdapter(FoundGames.this, list,
                            R.layout.event_row, new String[] {"eventName", "id", "username", "time"},
                            new int[] {R.id.eventName, R.id.userid, R.id.username, R.id.time}
                            );

                    //List<Map<String, String>> data = new ArrayList<Map<String, String>>();
                    /*
                    for(int i = 0; i < eventList.size(); i++){
                        Object object = eventList.get(i);
                        Map<String, String> datum = new HashMap<String, String>(2);
                        //String name = ((ParseObject) object).getObjectId().toString();
                        String name = ((ParseObject) object).getString("eventName");
                        String creator = ((ParseObject) object).getString("userName");
                        datum.put("event", name);
                        datum.put("creator", creator);

                    }
                    SimpleAdapter adapter = new SimpleAdapter(FoundGames.this, data,
                            android.R.layout.simple_list_item_1,
                            new String[] {"event", "creator"},
                            new int[] {android.R.id.text1,
                                    android.R.id.text2});

                    myListView.setAdapter(adapter);
                    */

                    myListView.setAdapter(adapter);

                    //myListView.setAdapter(arrayAdapter);

                } else {
                    Log.i("DEBUG", "No Events on day");
                    Toast.makeText(FoundGames.this, "No events found", Toast.LENGTH_SHORT).show();
                    //MAKE TOAST;
                }
                progressDialog.dismiss();
            }
        });
    }

}
