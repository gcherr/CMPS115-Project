package com.example.will.slugsports;

import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ArrayList<String> aList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aList = new ArrayList<String>();
        aList.add("Baseball");
        aList.add("Football");
        aList.add("Soccer");
        aList.add("Baseball");
        aList.add("Football");
        aList.add("Soccer");
        aList.add("Baseball");
        aList.add("Football");
        aList.add("Soccer");
        aList.add("Baseball");
        aList.add("Football");
        aList.add("Soccer");
        aList.add("Baseball");
        aList.add("Football");
        aList.add("Soccer");
        aList.add("Baseball");
        aList.add("Football");
        aList.add("Soccer");
        aList.add("Baseball");
        aList.add("Football");
        aList.add("Soccer");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1,aList
        );
        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(arrayAdapter);
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
}
