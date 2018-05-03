package com.example.david.snapmaps;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {
    private static final String Tag = "MainActivity";
    private static final int ERROR_DIALOGUE_REQUEST = 9001;
    public static final String TAG = "SNAPMAPS";
    EditText ed1,ed2;
    TextView tx1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isServicesOK()){
            init();
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
    private void init(){
        Button Map = (Button) findViewById(R.id.Map);
        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed1 = (EditText)findViewById(R.id.editText);
                ed2 = (EditText)findViewById(R.id.editText2);
                tx1 = (TextView)findViewById(R.id.wrongPass);
                tx1.setVisibility(View.GONE);

                ed1 = (EditText)findViewById(R.id.editText);
                ed2 = (EditText)findViewById(R.id.editText2);
                if(ed1.getText().toString().equals("admin") &&
                        ed2.getText().toString().equals("admin")) {
                    Log.d(TAG, "ButtonPressed");
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    Intent intent1 = new Intent();
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                        tx1.setVisibility(View.VISIBLE);
                        tx1.setBackgroundColor(Color.RED);
                }
            }

        });
    }
}



