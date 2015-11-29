package com.example.will.slugsports;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Arrays;

public class ViewEvent extends AppCompatActivity {

    Bundle bundle;
    String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        bundle = getIntent().getExtras();
        objectId = bundle.getString("objectId");
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

    public void joinEvent(View v){
        final Button button = (Button) findViewById(R.id.joinGame);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");

        // Retrieve the object by id
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject event, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    if(button.getText().toString().equalsIgnoreCase("Join")) {
                        event.put("numJoined", event.getNumber("numJoined").intValue() + 1);
                        event.addAllUnique("usersAttending", Arrays.asList(App.getAcct()));
                        Toast.makeText(ViewEvent.this, "Joined event", Toast.LENGTH_SHORT).show();
                        button.setText("Un-Join");
                    }
                    else{
                        event.put("numJoined", event.getNumber("numJoined").intValue() - 1);
                        Toast.makeText(ViewEvent.this, "Unjoined event", Toast.LENGTH_SHORT).show();
                        button.setText("Join");
                    }
                    event.saveInBackground();
                }
            }
        });
    }
}
