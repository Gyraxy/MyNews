package com.duboscq.nicolas.mynews.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.duboscq.nicolas.mynews.controllers.fragments.CustomNewsFragment;
import com.duboscq.nicolas.mynews.controllers.fragments.MostPopularFragment;
import com.duboscq.nicolas.mynews.controllers.fragments.TopStoriesFragment;

/**
 * Created by Nicolas DUBOSCQ on 24/07/2018
 */
public class PageAdapter extends FragmentPagerAdapter {

    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public int getCount() {
        return (3);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TopStoriesFragment.newInstance();
            case 1:
                return MostPopularFragment.newInstance();
            case 2:
                return CustomNewsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: //Page number 1
                return "TOP STORIES";
            case 1: //Page number 2
                return "MOST POPULAR";
            case 2: //Page number 3
                return "CUSTOM";
            default:
                return null;
        }
    }
}
