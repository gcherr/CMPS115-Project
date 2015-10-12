package com.example.will.slugsports;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ArrayList<String> spList, locList, dayList;
    private String currentList;
    private ListView myListView;
    private ArrayAdapter<String> arrayAdapter;
    private String LOG_TAG = "DEBUG: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spList = new ArrayList<String>();
        locList = new ArrayList<String>();
        dayList = new ArrayList<String>();
        populateSports(spList);
        populateLocs(locList);
        populateDays(dayList);
        arrayAdapter = new ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, spList
        );
        currentList = "Sports";
        myListView = (ListView) findViewById(R.id.listView);
        //Make each list element clickable
        populateListView(spList, "Sports");

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(MainActivity.this, spList.get(position), Toast.LENGTH_SHORT);
                toast.show();
                currentList = "Locations";
                populateListView(locList, "Locations");
            }
        });
        myListView.setAdapter(arrayAdapter);

    }

    @Override
    public void onBackPressed() {
        switch (currentList){
            case "Sports":
                new AlertDialog.Builder(this)
                        .setTitle("Really Exit?")
                        .setMessage("Are you sure you want to exit?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                MainActivity.super.onBackPressed();
                            }
                        }).create().show();
                //super.onBackPressed();
                break;
            case "Locations":
                //currentList = "Sports";
                populateListView(spList, "Sports");
                break;
            case "Days":
                //currentList = "Locations";
                populateListView(locList, "Locations");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void populateListView(ArrayList<String> list, String nextList){
        final ArrayList<String> list2 = list;
        currentList = nextList;
        arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, list2
        );
        changeTitle();
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(MainActivity.this, list2.get(position), Toast.LENGTH_SHORT);
                toast.show();
                switch(currentList){
                    case "Sports":
                        currentList = "Locations";
                        populateListView(locList, "Locations");
                        break;
                    case "Locations":
                        currentList = "Days";
                        populateListView(dayList, "Days");
                        break;
                    default:
                        break;
                }
                Log.i(LOG_TAG, currentList + " : ");
            }
        });
        myListView.setAdapter(arrayAdapter);
    }

    private void changeTitle(){
        TextView temp = (TextView) findViewById(R.id.textView);
        temp.setText(currentList + " List");
    }

    //Populate spList with the available sports
    private void populateSports(ArrayList<String> spList){
        spList.add("Baseball");
        spList.add("Football");
        spList.add("Soccer");
        spList.add("Basketball");
        spList.add("Hockey");
        spList.add("Frisbee Golf");
        spList.add("Tennis");
        spList.add("Swimming");
        spList.add("Weightlifting");
        spList.add("Boxing");
        spList.add("Golf");
    }

    //Populate locList with the available locations
    private void populateLocs(ArrayList<String> locList){
        locList.add("OPERS Field");
        locList.add("East Gym");
        locList.add("Tennis Courts");
        locList.add("Basketball Courts");
    }

    private void populateDays(ArrayList<String> dayList){
        dayList.add("Sunday");
        dayList.add("Monday");
        dayList.add("Tuesday");
        dayList.add("Wednesday");
        dayList.add("Thursday");
        dayList.add("Friday");
        dayList.add("Saturday");

    }
}
