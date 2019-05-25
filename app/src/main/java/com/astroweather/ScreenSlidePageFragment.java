package com.astroweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class ScreenSlidePageFragment extends Fragment {

    private TextView sunrise;
    private TextView currentTime;
    private ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment1_layout, container, false);

        sunrise = rootView.findViewById(R.id.textView2);
        currentTime = rootView.findViewById(R.id.textView3);

        TimerTask task = new TimerTask() {
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
                AstroCalculator.Location location = new AstroCalculator.Location(69.0, 42.0);
                AstroCalculator as = new AstroCalculator(dateTime, location);


                getActivity().runOnUiThread(()->{
                    sunrise.setText(as.getSunInfo().getSunrise().toString());
                    currentTime.setText(dateTime.toString());
                });

            }
        };

        Timer timer = new Timer(false);
        timer.scheduleAtFixedRate(task, 0, 1000);

        return rootView;
    }
}
