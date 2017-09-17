package com.example.mycongressapp;

/**
 * Created by Bhavana on 11/24/16.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FavoritesFragment extends Fragment{
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View x =  inflater.inflate(R.layout.favorite_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.fav_tabs);
        viewPager = (ViewPager) x.findViewById(R.id.fav_viewpager);

        viewPager.setAdapter(new FavoritesFragment.MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return x;

    }

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new Fav_Leg_Fragment();
                case 1 : return new Fav_Bills_Fragment();
                case 2 : return new Fav_Comm_Fragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }


        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Legislators";
                case 1 :
                    return "Bills";
                case 2 :
                    return "Committees";
            }
            return null;
        }
    }

}
