package com.astroweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button proceedbutton;
    private TextView latitude;
    private TextView longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        proceedbutton = findViewById(R.id.Proceed_button);
        latitude = findViewById(R.id.Latitude_Value_editText);
        longitude = findViewById(R.id.Longitude_Value_editText);

        proceedbutton.setOnClickListener(v -> {
            Intent nextScreen = new Intent(getApplicationContext(), ScreenSlideActivity.class);
            nextScreen.putExtra("latitude", Float.parseFloat(latitude.getText().toString()));
            nextScreen.putExtra("longitude", Float.parseFloat(longitude.getText().toString()));
            startActivity(nextScreen);
        });
    }

}
