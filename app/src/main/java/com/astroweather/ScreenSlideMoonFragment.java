package com.astroweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class ScreenSlideMoonFragment extends Fragment {

    private TextView moonrise;
    private TextView moonset;
    private TextView newmoon;
    private TextView fullmoon;
    private TextView illumination;
    private TextView synodic;
    private TextView currentTime;
    private ViewGroup rootView;

    private float latitude = 0;
    private float longitude = 0;
    private int refreshrate = 1000;

    private Timer timer;

    private static final double avg_synodic_day = 29.530587;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.moon_fragment_layout, container, false);

        ScreenSlideActivity ssa = (ScreenSlideActivity) getActivity();
        latitude = ssa.getLatitude();
        longitude = ssa.getLongitude();
        refreshrate = ssa.getRefreshrate() * 1000;

        moonrise = rootView.findViewById(R.id.Moonrise_Value_textView);
        moonset = rootView.findViewById(R.id.Moonset_Value_textView);
        newmoon = rootView.findViewById(R.id.Newmoon_Value_textView);
        fullmoon = rootView.findViewById(R.id.Fullmoon_Value_textView);
        illumination = rootView.findViewById(R.id.Moon_Illumination_Value_textView);
        synodic = rootView.findViewById(R.id.Moon_Age_Value_textView);
        currentTime = rootView.findViewById(R.id.Current_Time_textView);


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
                AstroCalculator.Location location = new AstroCalculator.Location(latitude, longitude);
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
                });
            }
        };

        timer = new Timer(false);
        timer.scheduleAtFixedRate(taskAstro, 0, refreshrate);
        timer.scheduleAtFixedRate(taskTime, 0, 1000);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}