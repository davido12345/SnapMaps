package com.example.david.snapmaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class PlacingMarker extends Activity {
    public static final String TAG = "CREATING";
    public static ArrayList<String> MarkerTitle = new ArrayList<>();
    public static ArrayList<String> MarkerDescription = new ArrayList<>();
    public static ArrayList<LatLng> k = new ArrayList<>();
    public static String markerTitleRecent;
    public static String markerDescriptionRecent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_placing_marker);
        super.onCreate(savedInstanceState);
        init();
    }
    private void init() {
        MapsActivity.creationStarted = true;
        Button Map = (Button) findViewById(R.id.confirm);
        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "ButtonPressed");
                createNewMarker();
                Intent intent = new Intent(PlacingMarker.this, MapsActivity.class);
                startActivity(intent);


            }
        });
    }
    private void createNewMarker(){
        EditText txtTitle =
                (EditText) findViewById(R.id.editTitle);
        String markerTitle = txtTitle.getText().toString();
        MapsActivity.userAddedTitles.add(markerTitle);


        EditText txtDescription =
                (EditText) findViewById(R.id.editDescript);
        String markerDescription = txtDescription.getText().toString();
        MapsActivity.userAddedDescriptions.add(markerDescription);
        Log.d(TAG, "initMap: MarkerData Created");

        MapsActivity.userAddedLatLng.add(MapsActivity.lastCamSite);


        Log.d(TAG, "initMap: MarkerData Created");

    }
}
