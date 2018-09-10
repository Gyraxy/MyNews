package com.duboscq.nicolas.mynews.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.duboscq.nicolas.mynews.controllers.fragments.CustomNewsFragment;
import com.duboscq.nicolas.mynews.controllers.fragments.MostPopularFragment;
import com.duboscq.nicolas.mynews.controllers.fragments.SearchNewsFragment;
import com.duboscq.nicolas.mynews.controllers.fragments.TopStoriesFragment;
import com.duboscq.nicolas.mynews.utils.SharedPreferencesUtility;

/**
 * Created by Nicolas DUBOSCQ on 24/07/2018
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    Context ctx;

    public ViewPagerAdapter(FragmentManager mgr, Context ctx) {
        super(mgr);
        this.ctx =ctx;
    }

    @Override
    public int getCount() {
        return (4);
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
            case 3:
                return SearchNewsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String weekly_tab = SharedPreferencesUtility.getInstance(ctx).getString("WEEKLY_SECTION_NAME","WEEKLY");

        switch (position){
            case 0: //Page number 1
                return "TOP STORIES";
            case 1: //Page number 2
                return "MOST POPULAR";
            case 2: //Page number 3
                return weekly_tab;
            case 3:
                return "SEARCH";
            default:
                return null;
        }
    }
}
