package com.astroweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button proceedbutton;
    private TextView latitude;
    private TextView longitude;
    private TextView refreshrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        proceedbutton = findViewById(R.id.Proceed_button);
        latitude = findViewById(R.id.Latitude_Value_editText);
        longitude = findViewById(R.id.Longitude_Value_editText);
        refreshrate = findViewById(R.id.Refresh_Rate_Value_editText);

        proceedbutton.setOnClickListener(v -> {
            float lat, lon;
            int rr;

            try {
                lat = Float.parseFloat(latitude.getText().toString());
            }
            catch(NumberFormatException nfe) {
                lat = -420;
            }
            try {
                lon = Float.parseFloat(longitude.getText().toString());
            }
            catch(NumberFormatException nfe) {
                lon = -420;
            }
            try {
                rr = Integer.parseInt(refreshrate.getText().toString());
            }
            catch(NumberFormatException nfe) {
                rr = 0;
            }
            if(lat > 180 || lat < -180 || lon > 180 || lon < -180 || rr < 1) {
                Toast.makeText(this, "Invalid data", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent nextScreen = new Intent(getApplicationContext(), ScreenSlideActivity.class);
                nextScreen.putExtra("latitude", lat);
                nextScreen.putExtra("longitude", lon);
                nextScreen.putExtra("refreshrate", rr);
                startActivity(nextScreen);
            }
        });
    }
}
