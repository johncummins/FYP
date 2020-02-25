package com.example.fyp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fyp.EventsTab;
import com.example.fyp.ExploreTab;
import com.example.fyp.FirstFloor;
import com.example.fyp.GalleryTab;
import com.example.fyp.GroundFloor;
import com.example.fyp.HomeTab;
import com.example.fyp.R;
import com.example.fyp.SecondFloor;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter_home extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_home, R.string.tab_text_explore, R.string.tab_text_events, R.string.tab_text_gallery, };
    private final Context mContext;

    public SectionsPagerAdapter_home(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        Fragment fragment = null;
        switch(position) {
            case 0:
                fragment = new HomeTab();
                break;
            case 1:
                fragment = new ExploreTab();
                break;
            case 2:
                fragment = new EventsTab();
                break;
            case 3:
                fragment = new GalleryTab();
                break;
        }

                return fragment;



    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}