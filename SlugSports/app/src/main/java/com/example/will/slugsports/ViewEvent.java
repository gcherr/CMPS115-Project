package com.example.will.slugsports;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewEvent extends AppCompatActivity {

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        bundle = getIntent().getExtras();
        setFields();
    }

    public void setFields(){
        TextView chosenSport = (TextView) findViewById(R.id.chosenSport);
        TextView gameTitle = (TextView) findViewById(R.id.gameTitle);
        TextView creator = (TextView) findViewById(R.id.gameCreator);
        TextView preferred = (TextView) findViewById(R.id.PreferredNum);
        TextView joined = (TextView) findViewById(R.id.NumJoined);
        TextView desc = (TextView) findViewById(R.id.desc);




    }
}
