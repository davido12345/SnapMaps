package com.example.david.snapmaps;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//import com.google.android.gms.location.places.GeoDataClient;
//import com.google.android.gms.location.places.PlaceDetectionClient;


import com.example.david.snapmaps.Databases.UserInfo;
import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.places.AutocompletePrediction;
//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.PlaceBuffer;
//import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnMarkerClickListener {

    private GoogleApiClient mGoogleApiClient;

    private GoogleMap mMap;
    private boolean isMarkerSelected;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionsGranted = false;
    private PlaceInfo mPlace;
    public static ArrayList<Marker> AllMarkers = new ArrayList<>();
    ArrayList<MarkerOptions> AllMarkersOptions = new ArrayList<>();
    public static ArrayList<Marker> userAddedMarkers = new ArrayList<>();
    public static ArrayList<LatLng> userAddedLatLng = new ArrayList<>();
    public static ArrayList<String> userAddedTitles = new ArrayList<>();
    public static ArrayList<String> userAddedDescriptions = new ArrayList<>();
    public static int addedThisSession;
    public static final String TAG = "MAPPING";
    LatLng location2 = new LatLng(-34, 151);
    LatLng mystery = new LatLng(40, -74);
    LatLng sydney = new LatLng(34, 51);
    LatLng sydney2 = new LatLng(-34, 150);
    public static ArrayList<LatLng> newMarkerLocation = new ArrayList<>();
    private Marker tobeDeleted;
    public static LatLng lastCamSite;
    public static boolean creationStarted=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        getLocationPermission();
    }

    private void initMap() {

         /* mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        */
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button Map = (Button) findViewById(R.id.markerMaker);//LOCATION
        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackCamera();
                addedThisSession++;
                Log.d(TAG, "ButtonPressed");
                Intent intent = new Intent(MapsActivity.this, Locations.class);
                startActivity(intent);
            }
        });

        Button Delete = (Button) findViewById(R.id.delButton);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //trackCamera();

                Log.d(TAG, "ButtonPressed");
                Intent intent = new Intent(MapsActivity.this, Events.class);
                startActivity(intent);
                /*tobeDeleted.remove();
                for(int i = 0; i<AllMarkers.size(); i++){
                    if(AllMarkers.get(i) == tobeDeleted){
                        AllMarkers.remove(i);
                    }
                }*/
            }

        });
    }

    private void trackCamera() {
        Log.d(TAG, "Getting Cam Coords");
        newMarkerLocation.add(mMap.getCameraPosition().target);
        lastCamSite= mMap.getCameraPosition().target;
    }//Inactive


    private void loadUserMarker(){

        AllMarkers.clear();

        for(int i = 0; i < UserInfo.locationNames.size(); i++) {
            Marker marker = mMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(UserInfo.latitudes.get(i), UserInfo.longitudes.get(i)))
                            .title(UserInfo.locationNames.get(i))
                            .snippet(UserInfo.comments.get(i)));
            AllMarkers.add(marker);
        }
    }
    private void loadFriendsMarkers(){

    }
    private void loadNewCreatedMarkers(){
        for(int i = 0; i < userAddedTitles.size(); i++) {
            Marker marker = mMap.addMarker(
                    new MarkerOptions()
                            .position(userAddedLatLng.get(i))
                            .title(userAddedTitles.get(i))
                            .snippet(userAddedDescriptions.get(i)));

            AllMarkers.add(marker);
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        loadUserMarker();
        loadNewCreatedMarkers();
        //createMarkers();
        Log.d(TAG, "initMap: PLACING MARKERS");
        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }//ACTIVE MAIN

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }//Inactive

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permission");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);

            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }//Inactive

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permissions failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permissions granted");
                    mLocationPermissionsGranted = true;
                    initMap();
                }
            }
        }
    }//Inactive

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        tobeDeleted = marker;
        for(int i =0; i<AllMarkers.size(); i++) {
            if (marker.equals(AllMarkers.get(i))){
                isMarkerSelected = true;
            }

        }
        return true;
    }//ACTIVE

}


