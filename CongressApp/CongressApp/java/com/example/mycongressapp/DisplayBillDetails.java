package com.example.mycongressapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bhavana on 11/22/16.
 */

public class DisplayBillDetails extends AppCompatActivity {
    String chamberurl="http://cs-server.usc.edu:45678/hw/hw8/images/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_bill);

        Intent intent = getIntent();
        final BillModel.ResultsBean bill_data = (BillModel.ResultsBean)intent.getSerializableExtra(ActiveBillsFragment.MESSAGE);
        TextView billid = (TextView) findViewById(R.id.bill_id);
        TextView title = (TextView) findViewById(R.id.bill_title);
        TextView billtype = (TextView) findViewById(R.id.bill_type);
        TextView billsponsor = (TextView) findViewById(R.id.bill_sponsor);
        TextView billchamber = (TextView) findViewById(R.id.bill_chamber);
        TextView  billstatus = (TextView) findViewById(R.id.bill_status);
        TextView  billintroducedon = (TextView) findViewById(R.id.bill_introduced_on);
        TextView  billcongressurl = (TextView) findViewById(R.id.bill_congress_url);
        TextView  billversion = (TextView) findViewById(R.id.bill_version_status);
        TextView  billurl = (TextView) findViewById(R.id.bill_url);
        ImageView  chamberPhoto= (ImageView) findViewById(R.id.billchamberImg);

        billid.setText(bill_data.getBill_id().toUpperCase());
        title.setText(bill_data.getShort_title() == null? bill_data.getOfficial_title() : bill_data.getShort_title());
        billtype.setText(bill_data.getBill_type().toUpperCase());
        billsponsor.setText(bill_data.getSponsor().getTitle() + " " + bill_data.getSponsor().getFirst_name() + " " + bill_data.getSponsor().getLast_name());
        billchamber.setText(bill_data.getChamber().substring(0,1).toUpperCase() + bill_data.getChamber().substring(1));
        billstatus.setText(bill_data.getHistory().isActive()? "Active" : "New");
        String bill_date = bill_data.getIntroduced_on();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = (Date)formatter.parse(bill_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("MMM dd, yyyy");
        bill_date = newFormat.format(date);
        billintroducedon.setText(bill_date);
        billcongressurl.setText(bill_data.getUrls().getCongress());
        billversion.setText(bill_data.getLast_version()== null? "None" : bill_data.getLast_version().getVersion_name());
        billurl.setText(bill_data.getLast_version()== null ? "None" : bill_data.getLast_version().getUrls().getPdf());
        String getChamber = bill_data.getChamber();
        if(getChamber.equals("house")){
            chamberurl += "h.png";
            Picasso.with(getApplicationContext()).load(chamberurl).into(chamberPhoto);
        }
        else{
            chamberPhoto.setImageResource(R.drawable.s);
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.bill_toolbar);
        myToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final CheckBox fav_button =(CheckBox) findViewById(R.id.bill_fav_star);
        final boolean isFavourite = readState(bill_data.getBill_id());
        fav_button.setChecked(isFavourite);
        fav_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (isFavourite) {
                    saveState(bill_data, false);

                } else {

                    saveState(bill_data, true);

                }

            }
        });



    }

    private void saveState(BillModel.ResultsBean bill_data,boolean isFavourite) {
        SharedPreferences billFav = this.getSharedPreferences(
                "Bill_Favourite", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor aSharedPreferencesEdit = billFav
                .edit();
        aSharedPreferencesEdit.putBoolean(bill_data.getBill_id(), isFavourite);
        aSharedPreferencesEdit.commit();


        SharedPreferences billFavData = this.getSharedPreferences(
                "Bill_Favourite_Data", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = billFavData.edit();

        if(isFavourite) {
            Gson gson = new Gson();
            String json = gson.toJson(bill_data);
            prefsEditor.putString(bill_data.getBill_id(), json);
        }
        else{
            prefsEditor.remove(bill_data.getBill_id());
        }
        prefsEditor.commit();
    }

    private boolean readState(String billid) {
        SharedPreferences billFav = this.getSharedPreferences(
                "Bill_Favourite", getApplicationContext().MODE_PRIVATE);
        return billFav.getBoolean(billid, false);
    }
}

