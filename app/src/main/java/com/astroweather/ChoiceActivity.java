package com.astroweather;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ChoiceActivity extends AppCompatActivity {

    private Button astrobutton;
    private Button weatherbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        astrobutton = findViewById(R.id.button);
        weatherbutton = findViewById(R.id.button2);

        astrobutton.setOnClickListener(v -> {
            Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(nextScreen);
        });

        weatherbutton.setOnClickListener(v -> {
            Intent nextScreen = new Intent(getApplicationContext(), LocationListActivity.class);
            startActivity(nextScreen);
        });

    }
}
