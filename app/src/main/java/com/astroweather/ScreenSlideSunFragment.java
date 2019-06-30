package com.astroweather;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
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

public class ScreenSlideSunFragment extends Fragment {

    private TextView sunrise_time;
    private TextView sunrise_azimuth;
    private TextView sunset_time;
    private TextView sunset_azimuth;
    private TextView twilight_morning;
    private TextView twilight_evening;
    private TextView latitude;
    private TextView longitude;
    private TextView currentTime;
    private ViewGroup rootView;

    private float lat = 0;
    private float lon = 0;
    private int refreshrate = 1000;

    private Timer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.sun_fragment_layout, container, false);

        ScreenSlideActivity ssa = (ScreenSlideActivity) getActivity();
        lat = ssa.getLatitude();
        lon = ssa.getLongitude();
        refreshrate = ssa.getRefreshrate() * 1000;

        sunrise_time = rootView.findViewById(R.id.Sunrise_Time_Value_textView);
        sunrise_azimuth = rootView.findViewById(R.id.Sunrise_Azimuth_Value_textView);
        sunset_time = rootView.findViewById(R.id.Sunset_Time_Value_textView);
        sunset_azimuth = rootView.findViewById(R.id.Sunset_Azimuth_Value_textView);
        twilight_morning = rootView.findViewById(R.id.Twilight_Morning_Value_textView);
        twilight_evening = rootView.findViewById(R.id.Twilight_Evening_Value_textView);
        latitude = rootView.findViewById(R.id.Latitude_textView);
        longitude = rootView.findViewById(R.id.Longitude_textView);
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
                AstroCalculator.Location location = new AstroCalculator.Location(lat, lon);
                AstroCalculator as = new AstroCalculator(dateTime, location);


                getActivity().runOnUiThread(()->{
                    sunrise_time.setText(as.getSunInfo().getSunrise().toString());
                    sunrise_azimuth.setText(Double.toString(as.getSunInfo().getAzimuthRise()));
                    sunset_time.setText(as.getSunInfo().getSunset().toString());
                    sunset_azimuth.setText(Double.toString(as.getSunInfo().getAzimuthSet()));
                    twilight_morning.setText(as.getSunInfo().getTwilightMorning().toString());
                    twilight_evening.setText(as.getSunInfo().getTwilightEvening().toString());
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

        latitude.setText("latitude: " + Float.toString(lat));
        longitude.setText("longitude: " + Float.toString(lon));

        timer = new Timer(false);
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
                    sunrise_time.setText(as.getSunInfo().getSunrise().toString());
                    sunrise_azimuth.setText(Double.toString(as.getSunInfo().getAzimuthRise()));
                    sunset_time.setText(as.getSunInfo().getSunset().toString());
                    sunset_azimuth.setText(Double.toString(as.getSunInfo().getAzimuthSet()));
                    twilight_morning.setText(as.getSunInfo().getTwilightMorning().toString());
                    twilight_evening.setText(as.getSunInfo().getTwilightEvening().toString());
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

        timer.scheduleAtFixedRate(taskAstro, 0, refreshrate);
        timer.scheduleAtFixedRate(taskTime, 0, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
