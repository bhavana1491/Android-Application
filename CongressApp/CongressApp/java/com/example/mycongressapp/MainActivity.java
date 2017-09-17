package com.example.mycongressapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view) ;


        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new LegislatorFragment()).commit();


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                android.support.v7.widget.Toolbar tool_bar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

                if (menuItem.getItemId() == R.id.nav_legislators) {

                    tool_bar.setTitle("Legislators");
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new LegislatorFragment()).commit();

                }

                else if (menuItem.getItemId() == R.id.nav_bills) {
                    tool_bar.setTitle("Bills");
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new BillFragment()).commit();
                }
                else if (menuItem.getItemId() == R.id.nav_committees) {
                    tool_bar.setTitle("Committees");
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new CommitteeFragment()).commit();
                }

                else if (menuItem.getItemId() == R.id.nav_favorites) {
                    tool_bar.setTitle("Favorites");
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new FavoritesFragment()).commit();
                }
                else if (menuItem.getItemId() == R.id.nav_aboutMe) {
                    Intent intent = new Intent(getApplicationContext(),AboutMe.class);
                    startActivity(intent);
                }

                return false;
            }

        });


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle DrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDrawerLayout.setDrawerListener(DrawerToggle);

        DrawerToggle.syncState();

    }
}