package com.astroweather;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class StatusDisplayFragment extends Fragment {

    private float latitude = 0;
    private float longitude = 0;
    private ViewGroup rootView;
    private TextView currentTime;
    private TextView latview;
    private TextView lonview;

    private Timer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.status_fragment_layout, container, false);

        ScreenSlideActivity ssa = (ScreenSlideActivity) getActivity();
        latitude = ssa.getLatitude();
        longitude = ssa.getLongitude();

        latview = rootView.findViewById(R.id.Status_Latitude_textView);
        lonview = rootView.findViewById(R.id.Status_Longitude_textView);
        currentTime = rootView.findViewById(R.id.Status_Time_textView);

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

        latview.setText("Latitude:" + Float.toString(latitude));
        lonview.setText("Longitude:" + Float.toString(longitude));

        timer = new Timer(false);
        timer.scheduleAtFixedRate(taskTime, 0, 1000);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
