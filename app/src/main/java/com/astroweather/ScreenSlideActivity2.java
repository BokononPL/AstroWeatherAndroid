package com.astroweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class ScreenSlideActivity2 extends FragmentActivity {
    private static final int NUM_PAGES = 2;
    private ViewPager mPager;

    private PagerAdapter pagerAdapter;


//    private float latitude;
//    private float longitude;
//    private int refreshrate;
//
//    public int getRefreshrate() {
//        return refreshrate;
//    }
//    public float getLatitude() {
//        return latitude;
//    }
//    public float getLongitude() {
//        return longitude;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_layout2);

        mPager = findViewById(R.id.pager2);

//        Fragment sunfragment = new ScreenSlideSunFragment();
//        Fragment moonfragment = new ScreenSlideMoonFragment();
//        Fragment statusfragment = new StatusDisplayFragment();

        Fragment weatherfragment = new WeatherFragment();
        Fragment forecastfragment = new ForecastFragment();


        if(mPager == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl1, weatherfragment)
                    .replace(R.id.fl2, forecastfragment)
                    .commit();
        }
        else {
            pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(pagerAdapter);
        }
//
//        latitude = getIntent().getFloatExtra("latitude", 0);
//        longitude = getIntent().getFloatExtra("longitude", 0);
//        refreshrate = getIntent().getIntExtra("refreshrate", 100);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return new WeatherFragment();
            }
            else {
                return new ForecastFragment();
            }

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
