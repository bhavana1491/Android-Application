package com.example.mycongressapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Bhavana on 11/22/16.
 */

public class DisplayLegislatorDetails extends AppCompatActivity {
    String chamberurl="http://cs-server.usc.edu:45678/hw/hw8/images/";
    String photourl = "";
    String partyurl="http://cs-server.usc.edu:45678/hw/hw8/images/";
    float diff=-1;
    Calendar start_term = new GregorianCalendar();
    Calendar end_term = new GregorianCalendar();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_legislators);

        Intent intent = getIntent();
        final LegislatorModel.ResultsBean leg_data = (LegislatorModel.ResultsBean)intent.getSerializableExtra(ActiveBillsFragment.MESSAGE);
        TextView legname = (TextView) findViewById(R.id.leg_name);
        TextView legemail = (TextView) findViewById(R.id.leg_email);
        TextView legchamber = (TextView) findViewById(R.id.leg_chamber);
        TextView legcontact = (TextView) findViewById(R.id.leg_contact);
        TextView  legstartterm = (TextView) findViewById(R.id.leg_start_term);
        TextView legendterm = (TextView) findViewById(R.id.leg_end_term);
        TextView  legoffice = (TextView) findViewById(R.id.leg_office);
        TextView  legstate = (TextView) findViewById(R.id.leg_state);
        TextView  legfax = (TextView) findViewById(R.id.leg_fax);
        TextView  legbirthday = (TextView) findViewById(R.id.leg_birthday);
        TextView  legparty = (TextView)  findViewById(R.id.leg_party);
        ImageView partySymbol = (ImageView) findViewById(R.id.party_photo);
        String getParty = leg_data.getParty();
        if(getParty.equals("R")){
            partyurl += "r.png";
            legparty.setText("Republican");
        }
        else{
            partyurl += "d.png";
            legparty.setText("Democrat");
        }
        Picasso.with(getApplicationContext()).load(partyurl).into(partySymbol);
        ImageView  LegPhoto= (ImageView) findViewById(R.id.leg_photo);
        photourl = "http://theunitedstates.io/images/congress/225x275/" + leg_data.getBioguide_id() + ".jpg";
        Picasso.with(getApplicationContext()).load(photourl).into(LegPhoto);
        legname.setText(leg_data.getTitle() + "." + leg_data.getLast_name() + ", " + leg_data.getFirst_name());
        legemail.setText(leg_data.getOc_email()== null? "N.A" : leg_data.getOc_email());
        legchamber.setText(leg_data.getChamber().substring(0,1).toUpperCase() + leg_data.getChamber().substring(1));
        legcontact.setText(leg_data.getPhone() == null ? "N.A" : leg_data.getPhone());
        String leg_start_date = leg_data.getTerm_start();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = (Date)formatter.parse(leg_start_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("MMM dd, yyyy");
        leg_start_date = newFormat.format(date);
        legstartterm.setText(leg_start_date);
        String leg_end_date = leg_data.getTerm_end();
        DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = (Date)formatter.parse(leg_end_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }




        SimpleDateFormat newFormat1 = new SimpleDateFormat("MMM dd, yyyy");
        leg_end_date = newFormat1.format(date1);
        legendterm.setText(leg_end_date);


        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Date today = new Date();
        float total = date1.getTime() - date.getTime();
        float present = today.getTime() - date.getTime();
        try {
            diff = ((present/total)* 100);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        progressBar.setProgress((int) diff);
        TextView progress_text = (TextView) findViewById(R.id.progressBarinsideText);
        progress_text.setText((int) diff + "%");
       // progressText.setText(diff+"%");
        legoffice.setText(leg_data.getOffice() == null ? "N.A" : leg_data.getOffice());
        legstate.setText(leg_data.getState().toUpperCase());
        legfax.setText(leg_data.getFax() == null ? "N.A" : leg_data.getFax());

        String leg_birthday = leg_data.getBirthday();
        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date2= null;
        try {
            date2 = (Date)formatter.parse(leg_birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newFormat2 = new SimpleDateFormat("MMM dd, yyyy");
        leg_birthday = newFormat2.format(date2);
        legbirthday.setText(leg_birthday);

        ImageView fb_img = (ImageView)findViewById(R.id.leg_fb_icon);
        fb_img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(leg_data.getfacebook_id()== null) {
                    Context context = getApplicationContext();
                    CharSequence text = "No facebook page!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else{
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.facebook.com/" + leg_data.getfacebook_id()));
                startActivity(intent);}
            }
        });



        ImageView twitter_img = (ImageView)findViewById(R.id.leg_twitter_icon);
        twitter_img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(leg_data.gettwitter_id()== null) {
                    Context context = getApplicationContext();
                    CharSequence text = "No Twitter page!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("http://www.twitter.com/" + leg_data.gettwitter_id()));
                    startActivity(intent);
                }
            }
        });

        ImageView website = (ImageView)findViewById(R.id.leg_website_icon);
        website.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(leg_data.getWebsite()== null) {
                    Context context = getApplicationContext();
                    CharSequence text = "No Website!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(leg_data.getWebsite()));
                    startActivity(intent);
                }
            }
        });

       Toolbar myToolbar = (Toolbar) findViewById(R.id.leg_toolbar);
        myToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final CheckBox fav_button =(CheckBox) findViewById(R.id.leg_fav_star);
        final boolean isFavourite = readState(leg_data.getBioguide_id());
        fav_button.setChecked(isFavourite);
        fav_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (isFavourite) {
                    saveState(leg_data, false);

                } else {

                    saveState(leg_data, true);

                }

            }
        });


    }
        private void saveState(LegislatorModel.ResultsBean leg_data, boolean isFavourite) {
        SharedPreferences legFav = this.getSharedPreferences(
                "Legislator_Favourite", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor aSharedPreferencesEdit = legFav
                .edit();
        aSharedPreferencesEdit.putBoolean(leg_data.getBioguide_id(), isFavourite);
        aSharedPreferencesEdit.commit();


        SharedPreferences legFavData = this.getSharedPreferences(
                "Leg_Favourite_Data", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = legFavData.edit();

        if(isFavourite) {
            Gson gson = new Gson();
            String json = gson.toJson(leg_data);
            prefsEditor.putString(leg_data.getBioguide_id(), json);
        }
        else{
            prefsEditor.remove(leg_data.getBioguide_id());
        }
        prefsEditor.commit();
    }

    private boolean readState(String bioid) {
        SharedPreferences legFav = this.getSharedPreferences(
                "Legislator_Favourite", getApplicationContext().MODE_PRIVATE);
        return legFav.getBoolean(bioid, false);
    }


    }


