package com.astroweather;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class ScreenSlideMoonFragment extends Fragment {

    private TextView moonrise;
    private TextView moonset;
    private TextView newmoon;
    private TextView fullmoon;
    private TextView illumination;
    private TextView synodic;
    private TextView latitude;
    private TextView longitude;
    private TextView currentTime;
    private ViewGroup rootView;

    private float lat = 0;
    private float lon = 0;
    private int refreshrate = 1000;

    private Timer timer;

    private static final double avg_synodic_day = 29.530587;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.moon_fragment_layout, container, false);

        ScreenSlideActivity ssa = (ScreenSlideActivity) getActivity();
        lat = ssa.getLatitude();
        lon = ssa.getLongitude();
        refreshrate = ssa.getRefreshrate() * 1000;

        moonrise = rootView.findViewById(R.id.Moonrise_Value_textView);
        moonset = rootView.findViewById(R.id.Moonset_Value_textView);
        newmoon = rootView.findViewById(R.id.Newmoon_Value_textView);
        fullmoon = rootView.findViewById(R.id.Fullmoon_Value_textView);
        illumination = rootView.findViewById(R.id.Moon_Illumination_Value_textView);
        synodic = rootView.findViewById(R.id.Moon_Age_Value_textView);
        latitude = rootView.findViewById(R.id.Latitude_textView);
        longitude = rootView.findViewById(R.id.Longitude_textView);
        currentTime = rootView.findViewById(R.id.Current_Time_textView);




        latitude.setText("latitude: " + Float.toString(lat));
        longitude.setText("longitude: " + Float.toString(lon));


        timer = new Timer(false);

        TimerTask taskAstro = new TimerTask() {
            @Override
            public void run() {
                TimeZone zone = TimeZone.getTimeZone("Europe/Warsaw");
                TimeZone.setDefault(zone);
                Calendar now = Calendar.getInstance();
                AstroDateTime dateTime = new AstroDateTime(
                        now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH),
                        now.get(Calendar.HOUR), now.get(Calendar.MINUTE), now.get(Calendar.SECOND),
                        (zone.getOffset(new Date().getTime()) / 1000 / 60 / 60 ) - 1, zone.inDaylightTime(new Date())
                );
                AstroCalculator.Location location = new AstroCalculator.Location(lat, lon);
                AstroCalculator as = new AstroCalculator(dateTime, location);


                getActivity().runOnUiThread(()->{
                    moonrise.setText(as.getMoonInfo().getMoonrise().toString());
                    moonset.setText(as.getMoonInfo().getMoonset().toString());
                    newmoon.setText(as.getMoonInfo().getNextNewMoon().toString());
                    fullmoon.setText(as.getMoonInfo().getNextFullMoon().toString());
                    illumination.setText(Double.toString(as.getMoonInfo().getIllumination() * 100));
                    synodic.setText(Double.toString(as.getMoonInfo().getAge()));
                });
            }
        };

        TimerTask taskTime = new TimerTask() {
            @Override
            public void run() {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Calendar now = Calendar.getInstance();

                getActivity().runOnUiThread(()->{
                    currentTime.setText(dateFormat.format((now.getTime())));
                    Log.println(Log.INFO, "test", "tick");
                });
            }
        };


        timer.scheduleAtFixedRate(taskAstro, 0, refreshrate);
        timer.scheduleAtFixedRate(taskTime, 0, 1000);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        timer.cancel();
        timer = new Timer(false);
        TimerTask taskTime = new TimerTask() {
            @Override
            public void run() {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Calendar now = Calendar.getInstance();

                getActivity().runOnUiThread(()->{
                    currentTime.setText(dateFormat.format((now.getTime())));
                    Log.println(Log.INFO, "test", "tick");
                });
            }
        };
        TimerTask taskAstro = new TimerTask() {
            @Override
            public void run() {
                TimeZone zone = TimeZone.getTimeZone("Europe/Warsaw");
                TimeZone.setDefault(zone);
                Calendar now = Calendar.getInstance();
                AstroDateTime dateTime = new AstroDateTime(
                        now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH),
                        now.get(Calendar.HOUR), now.get(Calendar.MINUTE), now.get(Calendar.SECOND),
                        (zone.getOffset(new Date().getTime()) / 1000 / 60 / 60 ) - 1, zone.inDaylightTime(new Date())
                );
                AstroCalculator.Location location = new AstroCalculator.Location(lat, lon);
                AstroCalculator as = new AstroCalculator(dateTime, location);


                getActivity().runOnUiThread(()->{
                    moonrise.setText(as.getMoonInfo().getMoonrise().toString());
                    moonset.setText(as.getMoonInfo().getMoonset().toString());
                    newmoon.setText(as.getMoonInfo().getNextNewMoon().toString());
                    fullmoon.setText(as.getMoonInfo().getNextFullMoon().toString());
                    illumination.setText(Double.toString(as.getMoonInfo().getIllumination() * 100));
                    synodic.setText(Double.toString(as.getMoonInfo().getAge()));
                });
            }
        };
        timer.scheduleAtFixedRate(taskAstro, 0, refreshrate);
        timer.scheduleAtFixedRate(taskTime, 0, 1000);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}