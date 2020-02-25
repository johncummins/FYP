package com.example.fyp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager ;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fyp.FirstFloor;
import com.example.fyp.GroundFloor;
import com.example.fyp.R;
import com.example.fyp.SecondFloor;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_11, R.string.tab_text_21, R.string.tab_text_31};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
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
                fragment = new GroundFloor();
                break;
            case 1:
                fragment = new FirstFloor();
                break;
            case 2:
                fragment = new SecondFloor();
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