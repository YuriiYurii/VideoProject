package com.yuriitsap.videoproject.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yuriitsap.videoproject.ui.fragment.ChooseTagFragment;
import com.yuriitsap.videoproject.ui.fragment.VideoGalleryFragment;

/**
 * Created by yuriitsap on 05.12.16.
 */
//Keep it simple stupid
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private static final int FRAGMENTS_COUNT = 2;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ChooseTagFragment.newInstance();
            case 1:
                return VideoGalleryFragment.newInstance("code");
        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Choose video tag!";
            case 1:
                return "Video Gallery";
        }
        return null;
    }

}
