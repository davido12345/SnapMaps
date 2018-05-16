package com.example.david.snapmaps;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import static com.example.david.snapmaps.MapsActivity.AllMarkers;

public class Locations extends AppCompatActivity {

    public static int addedThisSession;
    private String TAG = "Locations";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        checkBoxes();
        createButtons();

        fillText();
    }
    private void checkBoxes(){

    }
    private void createButtons() {

        Button Create = (Button) findViewById(R.id.locationCreate);
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedThisSession++;
                Log.d(TAG, "ButtonPressed");
                Intent intent = new Intent(Locations.this, PlacingMarker.class);
                startActivity(intent);
            }
        });
        Button Delete = (Button) findViewById(R.id.locationDelete);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ac = ((CheckBox) findViewById(R.id.checkBox1)).isChecked();
                boolean bc = ((CheckBox) findViewById(R.id.checkBox2)).isChecked();
                boolean cc = ((CheckBox) findViewById(R.id.checkBox3)).isChecked();
                boolean dc = ((CheckBox) findViewById(R.id.checkBox4)).isChecked();
                boolean ec = ((CheckBox) findViewById(R.id.checkBox5)).isChecked();
                boolean fc = ((CheckBox) findViewById(R.id.checkBox6)).isChecked();
                if(ac == true){
                    AllMarkers.remove(AllMarkers.size()-1);
                }
                if(bc == true){
                    AllMarkers.remove(AllMarkers.size()-2);
                }
                if(cc == true){
                    AllMarkers.remove(AllMarkers.size()-3);
                }
                if(dc == true){
                    AllMarkers.remove(AllMarkers.size()-4);
                }
                if(ec == true){
                    AllMarkers.remove(AllMarkers.size()-5);
                }
                if(fc == true){
                    AllMarkers.remove(AllMarkers.size()-6);
                }
                Intent intent = new Intent(Locations.this, Locations.class);
                startActivity(intent);
                Log.d(TAG, "ButtonPressed");

            }
        });
        Button Return = (Button) findViewById(R.id.locationReturnToMap);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedThisSession++;
                Log.d(TAG, "ButtonPressed");
                Intent intent = new Intent(Locations.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fillText() {
       if(AllMarkers.size() > 0) {
           TextView a = (TextView) findViewById(R.id.locationInfo1);
           a.setText(AllMarkers.get(AllMarkers.size()-1).getTitle() + " " + AllMarkers.get(AllMarkers.size()-1).getSnippet());

           }
       if(AllMarkers.size() > 1) {
            TextView b = (TextView) findViewById(R.id.locationInfo2);
            b.setText(AllMarkers.get(AllMarkers.size()-2).getTitle() + " " + AllMarkers.get(AllMarkers.size()-2).getSnippet());
       }
        if(AllMarkers.size() > 2) {
            TextView c = (TextView) findViewById(R.id.locationInfo3);
            c.setText(AllMarkers.get(AllMarkers.size()-3).getTitle() + " " + AllMarkers.get(AllMarkers.size()-3).getSnippet());
        }
        if(AllMarkers.size() > 3) {
            TextView d = (TextView) findViewById(R.id.locationInfo4);
            d.setText(AllMarkers.get(AllMarkers.size()-4).getTitle() + " " + AllMarkers.get(AllMarkers.size()-4).getSnippet());
        }
        if(AllMarkers.size() > 4) {
            TextView e = (TextView) findViewById(R.id.locationInfo5);
            e.setText(AllMarkers.get(AllMarkers.size()-5).getTitle() + " " + AllMarkers.get(AllMarkers.size()-5).getSnippet());
        }
        if(AllMarkers.size() > 5) {
            TextView f = (TextView) findViewById(R.id.locationInfo6);
            f.setText(AllMarkers.get(AllMarkers.size()-6).getTitle() + " " + AllMarkers.get(AllMarkers.size()-6).getSnippet());
        }

    }
}