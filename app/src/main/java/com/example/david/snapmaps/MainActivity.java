package com.example.david.snapmaps;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.david.snapmaps.Databases.InfoArrays;
import com.example.david.snapmaps.Databases.Keys;
import com.example.david.snapmaps.Databases.Links;
import com.example.david.snapmaps.Databases.UserInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    private static final String Tag = "MainActivity";
    private static final int ERROR_DIALOGUE_REQUEST = 9001;
    public static final String TAG = "SNAPMAPS";
    //private static InfoArrays infoArrays = new InfoArrays();
    EditText ed1, ed2;
    TextView tx1;
    JSONObject jsonObject = new JSONObject();
    TextView mTextView;
    String dataFromBase;
    Matcher m1;
    Matcher m2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        if (isServicesOK()) {
            init();
            Log.d(TAG, "about to start volley");
            JSonVolley(Links.allUserData);
            JSonVolley(Links.allLocationData);
            JSonVolley(Links.allUserLocationData);
            Log.d(TAG, "finished volley");
        }
    }

  public boolean isServicesOK() {

        Log.d(Tag, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            //user can make map requests
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(Tag, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOGUE_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();

        }
        return false;
    }
    private void JSonVolley(final String url) {

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "got a response");
                //manipulate response
                try {
                    for(int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        try {
                            JSonToArray(jsonObject, url);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);
    }
    private void JSonToArray (JSONObject jsonObject, String url) throws Exception {
        if(url.equals(Links.allUserData)) {

            InfoArrays.userIds.add(jsonObject.getInt(Keys.userId));
            InfoArrays.emails.add(jsonObject.getString(Keys.email));
            InfoArrays.passwords.add(jsonObject.getString(Keys.password));
            InfoArrays.firstNames.add(jsonObject.getString(Keys.firstName));
            InfoArrays.lastNames.add(jsonObject.getString(Keys.lastName));

        }else if(url.equals(Links.allLocationData)) {

            InfoArrays.locationIds.add(jsonObject.getInt(Keys.locationId));
            InfoArrays.locationNames.add(jsonObject.getString(Keys.locationName));
            InfoArrays.locationAddresses.add(jsonObject.getString(Keys.locationAddress));
            InfoArrays.latitudes.add(jsonObject.getDouble(Keys.latitude));
            InfoArrays.longitudes.add(jsonObject.getDouble(Keys.longitude));

        }else if(url.equals(Links.allUserLocationData)) {

            InfoArrays.userLocation_UserIds.add(jsonObject.getInt(Keys.userId));
            InfoArrays.userLocation_locationIds.add(jsonObject.getInt(Keys.locationId));
            InfoArrays.comments.add(jsonObject.getString(Keys.comments));
        }
        //Log.d(TAG, "getting size :" + InfoArrays.firstNames.size());
    }
    private void fillInUser(int userID) {

        //allUserData
        int index = InfoArrays.userIds.indexOf(userID);
        UserInfo.userId = userID;
        UserInfo.email = InfoArrays.emails.get(index);
        UserInfo.password = InfoArrays.passwords.get(index);
        UserInfo.firstName = InfoArrays.firstNames.get(index);
        UserInfo.lastName = InfoArrays.lastNames.get(index);

        //locationIDs and comments
        ArrayList<Integer> filteredLocationIds = new ArrayList<Integer>();
        for(int i = 0; i < InfoArrays.userLocation_UserIds.size(); i++) {
            if(InfoArrays.userLocation_UserIds.get(i) == userID) {
                filteredLocationIds.add(InfoArrays.userLocation_locationIds.get(i));
                UserInfo.comments.add(InfoArrays.comments.get(i));
            }
        }
        //location info
        for(int i = 0; i < filteredLocationIds.size(); i++) {
            for(int j = 0; j < InfoArrays.locationIds.size(); j++) {
                if(filteredLocationIds.get(i) == InfoArrays.locationIds.get(j)) {
                    UserInfo.locationIds.add(InfoArrays.locationIds.get(j));
                    UserInfo.locationNames.add(InfoArrays.locationNames.get(j));
                    UserInfo.locationAddresses.add(InfoArrays.locationAddresses.get(j));
                    UserInfo.latitudes.add(InfoArrays.latitudes.get(j));
                    UserInfo.longitudes.add(InfoArrays.longitudes.get(j));
                }
            }
        }

    }
    private void init() {



        Button Map = (Button) findViewById(R.id.Map);
        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tx1 = (TextView) findViewById(R.id.wrongPass);
                tx1.setVisibility(View.GONE);
                ed1 = (EditText)findViewById(R.id.editText);
                ed2 = (EditText)findViewById(R.id.editText2);

                int emailIndex = InfoArrays.emails.indexOf(ed1.getText().toString());
                int passwordIndex = InfoArrays.passwords.indexOf(ed2.getText().toString());
                //mTextView.setText(emailIndex + " : " + passwordIndex);
                if(emailIndex == passwordIndex && emailIndex > -1 && passwordIndex > -1) {
                    fillInUser(InfoArrays.userIds.get(emailIndex));
                    //int i = 4;
                    //mTextView.setText(UserInfo.userId + ": " + UserInfo.firstName + " " + UserInfo.lastName + "\n");
                    //mTextView.append(UserInfo.email + ": " + UserInfo.password + "\n");
                    //mTextView.append(UserInfo.locationIds.get(i) + ": " + UserInfo.locationNames.get(i) + "\n");
                    //mTextView.append(UserInfo.locationAddresses.get(i) + "\n");
                    //mTextView.append(UserInfo.latitudes.get(i) + " " + UserInfo.longitudes.get(i) + "\n");
                    //mTextView.append(UserInfo.comments.get(i));
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(intent);
                }else {
                    tx1.setVisibility(View.VISIBLE);
                    tx1.setBackgroundColor(Color.RED);
                }

            }

        });
    }
}


