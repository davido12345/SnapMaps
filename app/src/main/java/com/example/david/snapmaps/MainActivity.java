package com.example.david.snapmaps;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONArray;
import org.json.JSONObject;

import Database.Links;
import Database.UserDB;

import static Database.Keys.email;
import static Database.Keys.password;

public class MainActivity extends AppCompatActivity {
    private static final String Tag = "MainActivity";
    private static final int ERROR_DIALOGUE_REQUEST = 9001;
    public static final String TAG = "SNAPMAPS";
    EditText ed1, ed2;
    TextView tx1;
    UserDB userDB;
    JSONObject jsonObject = new JSONObject();
    TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textView5);

        if (isServicesOK()) {
            init();
            doAVolley("hello");
        }
    }

// ...

    public void doAVolley(String password) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://api.a17-sd501.studev.groept.be/get_first_name/1";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                       mTextView.setText("Response is: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
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

    private void init() {
        Button Map = (Button) findViewById(R.id.Map);
        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ed1 = (EditText)findViewById(R.id.editText);
                //ed2 = (EditText)findViewById(R.id.editText2);
                tx1 = (TextView) findViewById(R.id.wrongPass);
                tx1.setVisibility(View.GONE);
                //String name = "";
                try {

                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                //String ed1 = "eric.roose@student.kuleuven.be";
                //String ed2 = "er123";
                //try {
                //    userDB = new UserDB(ed1 /*.getText().toString()*/, ed2/*.getText().toString()*/);
                //}catch (Exception exc) {
                //    exc.printStackTrace();
                //}
                //if(ed1.getText().toString().equals("admin") &&
                //        ed2.getText().toString().equals("admin")) {
                Log.d(TAG, "ButtonPressed");
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                Intent intent1 = new Intent();
                startActivity(intent);
                //}else{
                //    Toast.makeText(getApplicationContext(), userDB.firstName,Toast.LENGTH_SHORT).show();
                Log.d(TAG, userDB.getFirstName());
                //        tx1.setVisibility(View.VISIBLE);
                //        tx1.setBackgroundColor(Color.RED);
                //}

            }

        });
    }
}


