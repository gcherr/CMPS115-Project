package com.example.will.slugsports;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;

public class ViewEvent extends AppCompatActivity {

    Bundle bundle;
    String objectId;
    ParseObject currentGame = null;
    String sport,title,creatr,pref,join,des = "";

    TextView chosenSport, gameTitle, creator, preferred, joined, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        chosenSport = (TextView) findViewById(R.id.chosenSport);
        gameTitle = (TextView) findViewById(R.id.gameTitle);
        creator = (TextView) findViewById(R.id.gameCreator);
        preferred = (TextView) findViewById(R.id.PreferredNum);
        joined = (TextView) findViewById(R.id.NumJoined);
        desc = (TextView) findViewById(R.id.desc);

        bundle = getIntent().getExtras();
        objectId = bundle.getString("objectId");
        findGame();
        //setFields();
    }

    public void setFields(){
        /*
        TextView chosenSport = (TextView) findViewById(R.id.chosenSport);
        TextView gameTitle = (TextView) findViewById(R.id.gameTitle);
        TextView creator = (TextView) findViewById(R.id.gameCreator);
        TextView preferred = (TextView) findViewById(R.id.PreferredNum);
        TextView joined = (TextView) findViewById(R.id.NumJoined);
        TextView desc = (TextView) findViewById(R.id.desc);
*/
        chosenSport.setText(sport);
        gameTitle.setText(title);
        creator.setText(creatr);
        preferred.setText(pref);
        joined.setText(join);
        desc.setText(des);

        //gameTitle.setText(currentGame.getString("eventName"));
        //creator.setText(currentGame.getString("userName"));
        //preferred.setText(currentGame.getInt("numPlayers")+"");
        //joined.setText(currentGame.getInt("numJoined")+"");
        //desc.setText(currentGame.getString("description"));


    }

    public void joinEvent(View v){

        final Button button = (Button) findViewById(R.id.joinGame);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");

        // Retrieve the object by id
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject event, ParseException e) {

                boolean alreadyJoined = false;

                JSONArray usersAttending = event.getJSONArray("usersAttending");

                for(int i = 0; i < usersAttending.length(); i++) {
                    try {

                        String user = usersAttending.get(i).toString();
                        if(user.equalsIgnoreCase(App.getAcct())) alreadyJoined = true;

                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }

               if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    if(!alreadyJoined) {
                        event.put("numJoined", event.getNumber("numJoined").intValue() + 1);
                        event.addAllUnique("usersAttending", Arrays.asList(App.getAcct()));

                        Toast.makeText(ViewEvent.this, "Joined event", Toast.LENGTH_SHORT).show();
                        button.setText("Un-Join");
                    }

                    else{
                        event.put("numJoined", event.getNumber("numJoined").intValue() - 1);
                        event.removeAll("usersAttending", Arrays.asList(App.getAcct()));

                        Toast.makeText(ViewEvent.this, "Unjoined event", Toast.LENGTH_SHORT).show();
                        button.setText("Join");
                    }
                    event.saveInBackground();
                }
            }
        });
    }

    public void findGame() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");

        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject event, ParseException e) {
                if (e == null) {
                    Toast.makeText(ViewEvent.this, event.getString("sport"), Toast.LENGTH_SHORT).show();
                    sport = event.getString("sport");
                    title = event.getString("eventName");
                    creatr = event.getString("userName");
                    pref = event.getString("numPlayers")+"";
                    join = event.getInt("numJoined")+"";
                    des = event.getString("description");

                    //Toast.makeText(ViewEvent.this, "sport is " + sport, Toast.LENGTH_SHORT).show();
                    Toast.makeText(ViewEvent.this, "pref is " + pref, Toast.LENGTH_SHORT).show();

                    //works
                    //TextView chosenSport = (TextView) findViewById(R.id.chosenSport);
                    //chosenSport.setText(sport);

                    setFields();
                    //ti
                    //cr
                    //pre
                    //join
                    //des
                }
                else{
                    Log.i("DEBUG", "Parse Error");
                    Toast.makeText(ViewEvent.this, "Parse Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
