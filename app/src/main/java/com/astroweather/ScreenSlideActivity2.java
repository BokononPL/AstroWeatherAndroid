package com.astroweather;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ScreenSlideActivity2 extends FragmentActivity {
    private static final int NUM_PAGES = 2;
    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

//    private String city;
//    private String country;
//    private Boolean isCelsius;

    private String id;

    public String getId() {
        return id;
    }

/*    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Boolean getCelsius() {
        return isCelsius;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_layout2);

        mPager = findViewById(R.id.pager2);

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

//        city = getIntent().getStringExtra("city");
//        country = getIntent().getStringExtra("country");
//        isCelsius = getIntent().getBooleanExtra("isCelsius", true);
        id = getIntent().getStringExtra("id");
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
