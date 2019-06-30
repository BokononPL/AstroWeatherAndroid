package com.astroweather;

import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity {

    private TextView location;
    private TextView current_weather;
    private TextView current_temperature;
    private TextView current_pressure;
    private TextView current_humidity;
    private TextView latitude;
    private TextView longitude;
    private ImageView current_weather_image;

    private static final String appid = "a9dae46044971ae4518fa00924c7cc6e";
    private static final double absolute_zero = -273.15;

    private boolean isCelsius = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_fragment_layout);

        location = findViewById(R.id.Location_textView);
        latitude = findViewById(R.id.Latitude_Value_textView);
        longitude = findViewById(R.id.Longitude_Value_textView);
        current_weather = findViewById(R.id.Current_Weather_textView);
        current_temperature = findViewById(R.id.Current_Temperature_Value_textView);
        current_weather_image = findViewById(R.id.Current_Weather_imageView);
        current_pressure = findViewById(R.id.Current_Pressure_Value_textView);
        current_humidity = findViewById(R.id.Current_Humidity_Value_textView);

        String city = "London";
        String country = "uk";

        String weather_url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s,%s&mode=json&appid=%s", city, country, appid);
        String response1 = "";
        new RequestWeatherAsyncTask().execute(weather_url, null, response1);

        String forecast_url = String.format("https://api.openweathermap.org/data/2.5/forecast?q=%s,%s&mode=json&appid=%s", city, country, appid);
        String response2 = "";
        new RequestForecastAsyncTask().execute(forecast_url, null, response2);
    }

    private class RequestWeatherAsyncTask extends AsyncTask<String, Void, String>{

        OkHttpClient client = new OkHttpClient();

        String request(String url) throws IOException {
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
        protected void onPostExecute(String s) {
            System.out.println(s);

            Gson gson = new Gson();
            WeatherData wd = gson.fromJson(s, WeatherData.class);

            System.out.println(wd);

            Glide.with(current_weather_image)
                .load(String.format("http://openweathermap.org/img/w/%s.png", wd.weather.get(0).icon))
                .into(current_weather_image);

            location.setText(wd.name + " (" + wd.sys.country + ")");
            longitude.setText("longitude: " + wd.coord.lon.toString());
            latitude.setText("latitude: " + wd.coord.lat.toString());
            current_weather.setText(wd.weather.get(0).description);
            if(isCelsius) {
                current_temperature.setText(Integer.toString((int)Math.round(wd.main.temp + absolute_zero)) + "°C");
            }
            else {
                current_temperature.setText(Integer.toString((int)Math.round((wd.main.temp + absolute_zero) * (9/5) + 32)) + "°F");
            }
            current_pressure.setText(Integer.toString(wd.main.pressure) + " hPa");
            current_humidity.setText(Integer.toString(wd.main.humidity) + "%");
        }
    }

    private class RequestForecastAsyncTask extends AsyncTask<String, Void, String>{

        OkHttpClient client = new OkHttpClient();

        String request(String url) throws IOException {
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
        protected void onPostExecute(String s) {
            System.out.println(s);

            Gson gson = new Gson();
            ForecastData fd = gson.fromJson(s, ForecastData.class);

            System.out.println(fd);

            /*
            Glide.with(current_weather_image)
                    .load(String.format("http://openweathermap.org/img/w/%s.png", wd.weather.get(0).icon))
                    .into(current_weather_image);

            location.setText(wd.name + " (" + wd.sys.country + ")");
            longitude.setText("longitude: " + wd.coord.lon.toString());
            latitude.setText("latitude: " + wd.coord.lat.toString());
            current_weather.setText(wd.weather.get(0).description);
            if(isCelsius) {
                current_temperature.setText(Integer.toString((int)Math.round(wd.main.temp + absolute_zero)) + "°C");
            }
            else {
                current_temperature.setText(Integer.toString((int)Math.round((wd.main.temp + absolute_zero) * (9/5) + 32)) + "°F");
            }
            current_pressure.setText(Integer.toString(wd.main.pressure) + " hPa");
            current_humidity.setText(Integer.toString(wd.main.humidity) + "%");
            */
        }
    }
}
