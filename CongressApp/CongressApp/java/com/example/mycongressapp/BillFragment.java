package com.example.mycongressapp;

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


public class BillFragment extends Fragment{

    public static TabLayout bill_Layout;
    public static ViewPager bill_viewPager;
    public static int int_items = 2 ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View x =  inflater.inflate(R.layout.bill_layout,null);
        bill_Layout = (TabLayout) x.findViewById(R.id.bill_tabs);
        bill_viewPager = (ViewPager) x.findViewById(R.id.bill_viewpager);

        bill_viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        bill_Layout.post(new Runnable() {
            @Override
            public void run() {
                bill_Layout.setupWithViewPager(bill_viewPager);
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
                case 0 : return new ActiveBillsFragment();
                case 1 : return new NewBillsFragment();

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
                    return "Active Bills";
                case 1 :
                    return "New Bills";
            }
            return null;
        }
    }

}

