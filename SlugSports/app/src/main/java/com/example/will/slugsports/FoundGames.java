package com.example.will.slugsports;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
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

    String OPERSloc, OPERSEvents = "";

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //
        extras = getIntent().getExtras();
        OPERSloc = extras.getString("location");
        //
        super.onCreate(savedInstanceState);

        progress = new ProgressDialog(this);
        progress.setMessage("Fetching OPERS data...");

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

                intent.putExtra("objectId", list.get(position).get("id"));

                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onResume(){
        super.onResume();

        //clear the list so events don't accumulate
        list.clear();
        myListView = (ListView) findViewById(R.id.listView2);
        SimpleAdapter adapter = new SimpleAdapter(FoundGames.this, list,
                R.layout.event_row, new String[] {"eventName", "id", "username", "time"},
                new int[] {R.id.eventName, R.id.userid, R.id.username, R.id.time}
        );
        myListView.setAdapter(adapter);

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

        query.orderByAscending("AM_PM");
        query.orderByAscending("hour");
        query.orderByAscending("minute");

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
                    Map<String, String> map;

                    for(int i = 0; i < eventList.size(); i++){
                        map = new HashMap<String, String>();
                        Object object = eventList.get(i);

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

                    SimpleAdapter adapter = new SimpleAdapter(FoundGames.this, list,
                            R.layout.event_row, new String[] {"eventName", "id", "username", "time"},
                            new int[] {R.id.eventName, R.id.userid, R.id.username, R.id.time}
                            );

                    myListView.setAdapter(adapter);


                } else {
                    Log.i("DEBUG", "No Events on day");
                    Toast.makeText(FoundGames.this, "No events found", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    public void showOPERSData(View v){
        new getCalData(App.getCred(), OPERSloc).execute();





    }

    public class getCalData extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;
        private Map<String, String> map;
        private String loc = "";

        public getCalData(GoogleAccountCredential credential, String location) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
            map = new HashMap<String, String>();

            //insert the OPERS calendar data
            map.put("East Field", "ucsc.edu_7265736f757263652d343432@resource.calendar.google.com");
            map.put("OPERS Pool", "ucsc.edu_7265736f757263652d343338@resource.calendar.google.com");
            map.put("East Field House Gym", "ucsc.edu_7265736f757263652d343435@resource.calendar.google.com");
            map.put("West Gym", "ucsc.edu_7265736f757263652d343535@resource.calendar.google.com");
        }

        /**
         * Background task to call Google Calendar API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of the next 10 events from the primary calendar.
         *
         * @return List of Strings describing returned events.
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            // List the next 10 events from the primary calendar.
            DateTime now = new DateTime(System.currentTimeMillis());
            List<String> eventStrings = new ArrayList<String>();
            Events events = mService.events().list(map.get(loc))
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();

            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    // All-day events don't have start times, so just use
                    // the start date.
                    start = event.getStart().getDate();
                }
                String description = event.getDescription();

                eventStrings.add(
                        String.format("%s ;%s; (%s)", event.getSummary(), description, start));
            }
            return eventStrings;
        }

        @Override
        protected void onPreExecute() {
            //mOutputText.setText("");
            progress.show();
        }

        @Override
        protected void onPostExecute(List<String> output) {
            progress.hide();
            OPERSEvents = TextUtils.join("\n", output);
            Toast.makeText(getApplicationContext(), OPERSEvents, Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(getParent())
                    .setTitle("OPERs events at " + OPERSloc)
                    .setMessage(OPERSEvents)
                    .setNegativeButton("Exit", null)
                    .create().show();
        }

        @Override
        protected void onCancelled() {
            progress.hide();
        }

    }
}
