package com.astroweather;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LocationListActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button addLocationButton;

    private LocationDao locationDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        recyclerView = (RecyclerView) findViewById(R.id.RV);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        ArrayList<Location> locations = new ArrayList<Location>();

        LocationDatabase db = LocationDatabase.getInstance(this.getApplicationContext());
        locationDao = db.locationDao();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RVAdapter(locations, getApplicationContext());
        recyclerView.setAdapter(mAdapter);


        addLocationButton = findViewById(R.id.Add_Favourite_button);
        addLocationButton.setOnClickListener(v -> {
            Intent nextScreen = new Intent(getApplicationContext(), WeatherInputActivity.class);
            startActivity(nextScreen);
        });


/*        AsyncTask.execute(() -> {
            locationDao.insert(new Location("Warsaw", "pl", null));
        });

        AsyncTask.execute(() -> {
            locationDao.insert(new Location("Berlin", "de", null));
        });*/


/*        AsyncTask.execute(() -> {
            locationDao.insert(new Location("Warsaw", "pl",3, null));
        });

        AsyncTask.execute(() -> {
            for(Location l : locationDao.getAllLocations()) {
                ((RVAdapter)mAdapter).addCity(l.getCity());
                ((RVAdapter)mAdapter).addCountry(l.getCountry());
            }
        });

        AsyncTask.execute(() -> {
            for(Location l : locationDao.getAllLocations()) {
                System.out.println("xD");
                System.out.println(l);
            }
        });*/

        new RVAsyncTask().execute();
    }

    private class RVAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for(Location l : locationDao.getAllLocations()) {
                ((RVAdapter)mAdapter).addLocation(l);
            }
            return null;
        }
    }
}
