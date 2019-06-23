package com.astroweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
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

    private TextView textView;
    private ImageView imageView;
    private static final String appid = "a9dae46044971ae4518fa00924c7cc6e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView = findViewById(R.id.Response_Code_textView);
        imageView = findViewById(R.id.imageView);

        String city = "London";
        String country = "uk";

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

            System.out.println(wd);
        }
    }
}
