package com.astroweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

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

        mPager = (ViewPager) findViewById(R.id.pager);

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

            // Instantiate a ViewPager and a PagerAdapter.

            pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(pagerAdapter);
        }

        latitude = getIntent().getFloatExtra("latitude", 0);
        longitude = getIntent().getFloatExtra("longitude", 0);
        refreshrate = getIntent().getIntExtra("refreshrate", 1);
    }

    /*
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    */

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
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
