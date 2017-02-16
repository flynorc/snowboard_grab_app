package com.flynorc.snowboardgrabsquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class ExplainGrabsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_grabs);
        ArrayList<SnowboardGrab> grabs = new SnowboardGrabs(this).getSnowboardGrabs();
        //add the general grab explanations only needed in this activity
        grabs.add(0, new SnowboardGrab(getString(R.string.back_hand_grabs_title), getString(R.string.back_hand_grabs_description), true, R.drawable.regular_back_hand_grabs));
        grabs.add(0, new SnowboardGrab(getString(R.string.front_hand_grabs_title), getString(R.string.front_hand_grabs_description), true, R.drawable.regular_front_hand_grabs));

        SnowboardGrabAdapter grabsAdapter = new SnowboardGrabAdapter(this, grabs);
        ListView listView = (ListView) findViewById(R.id.activity_explain_grabs);
        listView.setAdapter(grabsAdapter);

    }
}
