package com.astroweather;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherInputActivity extends AppCompatActivity {

    private TextView city;
    private TextView country;
    private RadioButton celsius;
    private RadioButton fahrenheit;
    private Button proceedbutton;

    private boolean isCelsius = true;

    private static final String appid = "a9dae46044971ae4518fa00924c7cc6e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_input_layout);

        proceedbutton = findViewById(R.id.Weather_Proceed_button);
        city = findViewById(R.id.City_Input_editText);
        country = findViewById(R.id.Country_Input_editText);
        celsius = findViewById(R.id.Celsius_radioButton);
        fahrenheit = findViewById(R.id.Fahrenheit_radioButton);

        celsius.setOnClickListener(v -> {
            isCelsius = true;
        });

        fahrenheit.setOnClickListener(V -> {
            isCelsius = false;
        });

        proceedbutton.setOnClickListener(v -> {
            String t1 = city.getText().toString();
            String t2 = country.getText().toString();
            String weather_url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s,%s&mode=json&appid=%s", t1, t2, appid);

            String response = "";
            try {
                response = new RequestWeatherAsyncTask().execute(weather_url, null, response).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(t1.length() < 1 || t2.length() < 1 || response.compareTo("{\"cod\":\"404\",\"message\":\"city not found\"}") == 0) {
                Toast.makeText(this, "Invalid data", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent nextScreen = new Intent(getApplicationContext(), ScreenSlideActivity2.class);
                nextScreen.putExtra("city", t1);
                nextScreen.putExtra("country", t2);
                nextScreen.putExtra("isCelsius", true);
                startActivity(nextScreen);
            }
        });
    }

    private class RequestWeatherAsyncTask extends AsyncTask<String, Void, String> {

        String request(String url) throws IOException {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = "";

            try {
                response = request(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) { }
    }
}
