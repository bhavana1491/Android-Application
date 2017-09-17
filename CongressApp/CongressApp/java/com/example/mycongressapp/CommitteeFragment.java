package com.example.mycongressapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CommitteeFragment extends Fragment{

    public static TabLayout committee_Layout;
    public static ViewPager committee_viewPager;
    public static int int_items = 3 ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View x =  inflater.inflate(R.layout.committee_layout,null);
        committee_Layout = (TabLayout) x.findViewById(R.id.committee_tabs);
        committee_viewPager = (ViewPager) x.findViewById(R.id.committee_viewpager);

        committee_viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        committee_Layout.post(new Runnable() {
            @Override
            public void run() {
                committee_Layout.setupWithViewPager(committee_viewPager);
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
                case 0 : return new CommitteeHouseFragment();
                case 1 : return new CommitteeSenateFragment();
                case 2 : return new CommitteeJointFragment();

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
                    return "House";
                case 1 :
                    return "Senate";

                case 2 :
                    return "Joint";
            }
            return null;
        }
    }



}


