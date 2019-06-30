package com.astroweather;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ScreenSlideActivity extends FragmentActivity {
    private static final int NUM_PAGES = 2;
    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

    private float latitude;
    private float longitude;
    private int refreshrate;

    public int getRefreshrate() {
        return refreshrate;
    }
    public float getLatitude() {
        return latitude;
    }
    public float getLongitude() {
        return longitude;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_layout);

        mPager = findViewById(R.id.pager);

        Fragment sunfragment = new ScreenSlideSunFragment();
        Fragment moonfragment = new ScreenSlideMoonFragment();
        Fragment statusfragment = new StatusDisplayFragment();

        if(mPager == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl1,sunfragment)
                    .replace(R.id.fl2, moonfragment)
                    .replace(R.id.fl3, statusfragment)
                    .commit();
        }
        else {
            pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(pagerAdapter);
        }

        latitude = getIntent().getFloatExtra("latitude", 0);
        longitude = getIntent().getFloatExtra("longitude", 0);
        refreshrate = getIntent().getIntExtra("refreshrate", 1);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return new ScreenSlideSunFragment();
            }
            else {
                return new ScreenSlideMoonFragment();
            }

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
