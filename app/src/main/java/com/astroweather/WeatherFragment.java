package com.astroweather;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherFragment extends Fragment {

    private TextView location;
    private TextView current_weather;
    private TextView current_temperature;
    private TextView current_pressure;
    private TextView current_humidity;
    private TextView latitude;
    private TextView longitude;
    private ImageView current_weather_image;

    private Button refresh_button;

    private static final String appid = "a9dae46044971ae4518fa00924c7cc6e";
    private static final double absolute_zero = -273.15;

    public String city = "";
    public String country = "";
    public Boolean isCelsius = true;
    public String id;

    public String data = "";

    private ViewGroup rootView;

    private ScreenSlideActivity2 ssa = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.weather_fragment_layout, container, false);

        ssa = (ScreenSlideActivity2) getActivity();
//        city = ssa.getCity().toLowerCase();
//        country = ssa.getCountry().toLowerCase();
//        isCelsius = ssa.getCelsius();
        id = ssa.getId();

        Location l = null;
        try {
            l = new DatabaseAccessAsyncTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        city = l.getCity();
        country = l.getCountry();

        location = rootView.findViewById(R.id.Location_textView);
        latitude = rootView.findViewById(R.id.Latitude_Value_textView);
        longitude = rootView.findViewById(R.id.Longitude_Value_textView);
        current_weather = rootView.findViewById(R.id.Current_Weather_textView);
        current_temperature = rootView.findViewById(R.id.Current_Temperature_Value_textView);
        current_weather_image = rootView.findViewById(R.id.Current_Weather_imageView);
        current_pressure = rootView.findViewById(R.id.Current_Pressure_Value_textView);
        current_humidity = rootView.findViewById(R.id.Current_Humidity_Value_textView);

        refresh_button = rootView.findViewById(R.id.Weather_Refresh_button);

        String weather_url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s,%s&mode=json&appid=%s", city, country, appid);
        String response1 = "";
        new RequestWeatherAsyncTask().execute(weather_url, null, response1);

        refresh_button.setOnClickListener(v -> {
            new RequestWeatherAsyncTask().execute(weather_url, null, response1);
            Toast.makeText(ssa, "Refreshing data", Toast.LENGTH_SHORT).show();
        });

        return rootView;
    }

    private class DatabaseAccessAsyncTask extends AsyncTask<String, Void, Location> {
        @Override
        protected void onPostExecute(Location location) {
            super.onPostExecute(location);
        }

        @Override
        protected Location doInBackground(String... strings) {
            LocationDatabase db = LocationDatabase.getInstance(ssa.getApplicationContext());
            LocationDao locationDao = db.locationDao();

            Location l = locationDao.findById(strings[0]).get(0);
            return l;
        }
    }

    private class RequestWeatherAsyncTask extends android.os.AsyncTask<String, Void, String> {

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
                data = response;

                LocationDatabase db = LocationDatabase.getInstance(ssa.getApplicationContext());
                LocationDao locationDao = db.locationDao();

                Location l = locationDao.findById(id).get(0);
                l.setData(response);
                locationDao.update(l);

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

            //System.out.println(wd);
            try {
                Glide.with(current_weather_image)
                        .load(String.format("http://openweathermap.org/img/w/%s.png", wd.weather.get(0).icon))
                        .into(current_weather_image);

                location.setText(wd.name + " (" + wd.sys.country + ")");
                longitude.setText("longitude: " + wd.coord.lon.toString());
                latitude.setText("latitude: " + wd.coord.lat.toString());
                current_weather.setText(wd.weather.get(0).description);
                if (isCelsius) {
                    current_temperature.setText(Integer.toString((int) Math.round(wd.main.temp + absolute_zero)) + "°C");
                } else {
                    current_temperature.setText(Integer.toString((int) Math.round((wd.main.temp + absolute_zero) * (9 / 5) + 32)) + "°F");
                }
                current_pressure.setText(Integer.toString(wd.main.pressure) + " hPa");
                current_humidity.setText(Integer.toString(wd.main.humidity) + "%");
            }
            catch (NullPointerException npe){
                System.out.println("communication failed");
            }

/*            LocationDatabase db = LocationDatabase.getInstance(ssa.getApplicationContext());
            LocationDao locationDao = db.locationDao();

            AsyncTask.execute(() -> {
                for(Location l : locationDao.getAllLocations()) {
                    System.out.println("xD");
                    System.out.println(l);
                }
            });*/

        }
    }
}
