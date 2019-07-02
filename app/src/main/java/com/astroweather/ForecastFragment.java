package com.astroweather;

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

public class ForecastFragment extends Fragment {

    private ViewGroup rootView;

    private TextView location;
    private TextView latitude;
    private TextView longitude;
    private TextView day1_title;
    private TextView day1_description;
    private TextView day1_temperature;
    private TextView day2_title;
    private TextView day2_description;
    private TextView day2_temperature;
    private TextView day3_title;
    private TextView day3_description;
    private TextView day3_temperature;

    private ImageView day1_image;
    private ImageView day2_image;
    private ImageView day3_image;

    private Button refresh_button;


    private static final String appid = "a9dae46044971ae4518fa00924c7cc6e";
    private static final double absolute_zero = -273.15;

    public String city = "";
    public String country = "";
    public Boolean isCelsius = true;
    public String id;

    private ScreenSlideActivity2 ssa = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.forecast_fragment_layout, container, false);

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

        day1_title = rootView.findViewById(R.id.Day1_Title_textView);
        day1_description = rootView.findViewById(R.id.Day1_Description_textView);
        day1_temperature = rootView.findViewById(R.id.Day1_Temperature_textView);
        day1_image = rootView.findViewById(R.id.Day1_imageView);
        day2_title = rootView.findViewById(R.id.Day2_Title_textView);
        day2_description = rootView.findViewById(R.id.Day2_Description_textView);
        day2_temperature = rootView.findViewById(R.id.Day2_Temperature_textView);
        day2_image = rootView.findViewById(R.id.Day2_imageView);
        day3_title = rootView.findViewById(R.id.Day3_Title_textView);
        day3_description = rootView.findViewById(R.id.Day3_Description_textView);
        day3_temperature = rootView.findViewById(R.id.Day3_Temperature_textView);
        day3_image = rootView.findViewById(R.id.Day3_imageView);

        refresh_button = rootView.findViewById(R.id.Forecast_Refresh_button);


        String forecast_url = String.format("https://api.openweathermap.org/data/2.5/forecast?q=%s,%s&mode=json&appid=%s", city, country, appid);
        String response2 = "";
        new RequestForecastAsyncTask().execute(forecast_url, null, response2);

        refresh_button.setOnClickListener(v -> {
            new RequestForecastAsyncTask().execute(forecast_url, null, response2);
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

    private class RequestForecastAsyncTask extends AsyncTask<String, Void, String> {

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

            //System.out.println(fd);

            try {
                location.setText(fd.city.name + " (" + fd.city.country + ")");
                longitude.setText("longitude: " + (String.format("%.2f", fd.city.coord.lon)));
                latitude.setText("latitude: " + String.format("%.2f", fd.city.coord.lat));

                int i = 3;
                do {
                    if (fd.list.get(i).dt_txt.split(" ")[1].compareTo("12:00:00") == 0) {
                        break;
                    } else {
                        i++;
                    }
                } while (true);

                Glide.with(day1_image)
                        .load(String.format("http://openweathermap.org/img/w/%s.png", fd.list.get(i).weather.get(0).icon))
                        .into(day1_image);

                Glide.with(day2_image)
                        .load(String.format("http://openweathermap.org/img/w/%s.png", fd.list.get(i + 8).weather.get(0).icon))
                        .into(day2_image);

                Glide.with(day3_image)
                        .load(String.format("http://openweathermap.org/img/w/%s.png", fd.list.get(i + 16).weather.get(0).icon))
                        .into(day3_image);

                day1_title.setText(fd.list.get(i).dt_txt);
                day2_title.setText(fd.list.get(i + 8).dt_txt);
                day3_title.setText(fd.list.get(i + 16).dt_txt);

                day1_description.setText(fd.list.get(i).weather.get(0).description);
                day2_description.setText(fd.list.get(i + 8).weather.get(0).description);
                day3_description.setText(fd.list.get(i + 16).weather.get(0).description);

                if (isCelsius) {
                    day1_temperature.setText(Integer.toString((int) Math.round(fd.list.get(i).main.temp + absolute_zero)) + "°C");
                    day2_temperature.setText(Integer.toString((int) Math.round(fd.list.get(i + 8).main.temp + absolute_zero)) + "°C");
                    day3_temperature.setText(Integer.toString((int) Math.round(fd.list.get(i + 16).main.temp + absolute_zero)) + "°C");
                } else {
                    day1_temperature.setText(Integer.toString((int) Math.round((fd.list.get(i).main.temp + absolute_zero) * (9 / 5) + 32)) + "°F");
                    day2_temperature.setText(Integer.toString((int) Math.round((fd.list.get(i + 8).main.temp + absolute_zero) * (9 / 5) + 32)) + "°F");
                    day3_temperature.setText(Integer.toString((int) Math.round((fd.list.get(i + 16).main.temp + absolute_zero) * (9 / 5) + 32)) + "°F");
                }
            }
            catch (NullPointerException npe) {
                System.out.println("communication failed");
            }
        }
    }
}
