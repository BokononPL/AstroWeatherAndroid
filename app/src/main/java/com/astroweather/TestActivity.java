package com.astroweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

        String city = "London";
        String country = "uk";
        String appid = "a9dae46044971ae4518fa00924c7cc6e";

        String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s,%s&mode=json&appid=%s", city, country, appid);

        String response = "";
        new RequestAsyncTask().execute(url, null, response);
    }

    private class RequestAsyncTask extends AsyncTask<String, Void, String>{

        OkHttpClient client = new OkHttpClient();

        String request(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

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
            Glide.with(imageView)
                    .load(String.format("http://openweathermap.org/img/w/%s.png", wd.weather.get(0).icon))
                .into(imageView);
            textView.setText(wd.cod.toString());
        }
    }

    public class WeatherData {

        public Coord coord;
        public List<Weather> weather = null;
        public String base;
        public Main main;
        public Integer visibility;
        public Wind wind;
        public Rain rain;
        public Snow snow;
        public Clouds clouds;
        public Integer dt;
        public Sys sys;
        public Integer id;
        public String name;
        public Integer cod;
        public WeatherData() {
        }

        public class Clouds {

            public Integer all;
            public Clouds() {
            }

        }

        public class Coord {

            public Double lon;
            public Double lat;

            public Coord() {
            }

        }

        public class Main {

            public Double temp;
            public Integer pressure;
            public Integer humidity;
            public Double tempMin;
            public Double tempMax;

            public Main() {
            }
        }

        public class Rain {

            @SerializedName("3h")
            public Double _3h;
            @SerializedName("1h")
            public Double _1h;

            public Rain() {
            }
        }

        public class Snow {

            @SerializedName("3h")
            public Double _3h;
            public Snow() {
            }

        }

        public class Sys {

            public Integer type;
            public Integer id;
            public Double message;
            public String country;
            public Integer sunrise;
            public Integer sunset;

            public Sys() {
            }

        }

        public class Weather {

            public Integer id;
            public String main;
            public String description;
            public String icon;

            public Weather() {
            }

        }

        public class Wind {

            public Double speed;
            public Integer deg;

            public Wind() {
            }

        }
    }
}
