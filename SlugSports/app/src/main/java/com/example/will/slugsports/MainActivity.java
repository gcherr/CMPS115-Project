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

    private ArrayList<String> spList, locList, dayList, hourList;
    private String currentList;
    private ListView myListView;
    private ArrayAdapter<String> arrayAdapter;
    private String LOG_TAG = "DEBUG: ";
    private String chosenSp = "";
    private String chosenLoc = "";
    private String chosenDay = "";
    private String chosenHour ="";
    private String chosenCriteria = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spList = new ArrayList<String>();
        locList = new ArrayList<String>();
        dayList = new ArrayList<String>();
        hourList = new ArrayList<String>();
        populateSports(spList);
        populateLocs(locList);
        populateDays(dayList);
        populateHours(hourList);
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
                chosenSp = spList.get(position);
                chosenCriteria = chosenSp;
                Toast toast = Toast.makeText(MainActivity.this, chosenSp, Toast.LENGTH_SHORT);
                toast.show();
                currentList = "Locations";
                populateListView(locList, "Locations");
                updateChosenView();
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
            case "Hours":
                //currentList = "Days";
                populateListView(dayList,"Days");
                break;
            default:
                break;
        }
        updateChosenView();
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

    //populate list with the given criteria
    //also adds a listener to link to the next set of criteria (if available)
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
        Toast toast;
                switch(currentList){
                    case "Sports":
                        chosenSp =  list2.get(position);
                        currentList = "Locations";
                        chosenCriteria = list2.get(position);
                        populateListView(locList, "Locations");
                        toast = Toast.makeText(MainActivity.this, list2.get(position), Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                    case "Locations":
                        chosenLoc = list2.get(position);
                        currentList = "Days";
                        chosenCriteria = chosenSp + " : " + list2.get(position);
                        populateListView(dayList, "Days");
                        toast = Toast.makeText(MainActivity.this, chosenSp + " : " + list2.get(position), Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                    case "Days":
                        chosenDay = list2.get(position);
                        currentList = "Hours";
                        chosenCriteria = chosenSp + " : " + chosenLoc + " : " + list2.get(position);
                        populateListView(hourList, "Hours");
                        toast = Toast.makeText(MainActivity.this, chosenSp + " : " + chosenLoc + " : " + list2.get(position), Toast.LENGTH_SHORT);
                        toast.show();
                    case "Hours":
                        chosenHour = list2.get(position);
                        chosenCriteria = chosenSp + " : " + chosenLoc + " : " + chosenDay + " : " + list2.get(position);
                        toast = Toast.makeText(MainActivity.this, chosenSp + " : " + chosenLoc + " : " + chosenDay + " : " + list2.get(position), Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                    default:
                        break;
                }
                updateChosenView();
                Log.i(LOG_TAG, currentList + " : ");
            }
        });
        myListView.setAdapter(arrayAdapter);
    }

    private void changeTitle(){
        TextView temp = (TextView) findViewById(R.id.titleView);
        temp.setText(currentList + " List");
    }

    private void updateChosenView(){
        TextView temp = (TextView) findViewById(R.id.textView2);
        temp.setText(chosenCriteria);
    }

    //Populate spList with the available sports
    private void populateSports(ArrayList<String> spList){
        spList.add("Baseball");
        spList.add("Basketball");
        spList.add("Football");
        spList.add("Frisbee Golf");
        spList.add("Futsal");
        spList.add("Racket Ball");
        spList.add("Soccer");
        spList.add("Swimming");
        spList.add("Tennis");
        spList.add("Weightlifting");
    }

    //Populate locList with the available locations
    private void populateLocs(ArrayList<String> locList){
        locList.add("College 8 Basketball Courts");
        locList.add("College 8 Tennis Courts");
        locList.add("OPERS Basketball Courts");
        locList.add("OPERS Field");
        locList.add("OPERS Gym");
        locList.add("OPERS Pool");
        locList.add("OPERS Tennis Courts");
    }

    //Populate dayList with the available days
    private void populateDays(ArrayList<String> dayList){
        dayList.add("Sunday");
        dayList.add("Monday");
        dayList.add("Tuesday");
        dayList.add("Wednesday");
        dayList.add("Thursday");
        dayList.add("Friday");
        dayList.add("Saturday");

    }

    //Populate hourList with the available hours
    private void populateHours(ArrayList<String> hourList){
        hourList.add("6 am");
        hourList.add("7 am");
        hourList.add("8 am");
        hourList.add("9 am");
        hourList.add("10 am");
        hourList.add("11 am");
        hourList.add("12 pm");
        hourList.add("12 am");
        hourList.add("1 pm");
        hourList.add("2 pm");
        hourList.add("3 pm");
        hourList.add("4 pm");
        hourList.add("5 pm");
        hourList.add("6 pm");
        hourList.add("7 pm");
        hourList.add("8 pm");
        hourList.add("9 pm");
        hourList.add("10 pm");
        hourList.add("11 pm");
    }
}
