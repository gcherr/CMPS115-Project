package com.example.will.slugsports;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;



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
        Button b = (Button) v;
        String dest;

        Intent intent = new Intent(FoundGames.this, createEvent.class);

        intent.putExtras(extras);

        startActivity(intent);
    }

}
