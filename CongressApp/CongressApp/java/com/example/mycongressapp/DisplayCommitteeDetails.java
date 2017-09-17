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

/**
 * Created by Bhavana on 11/22/16.
 */

public class DisplayCommitteeDetails extends AppCompatActivity {
    String chamberurl="http://cs-server.usc.edu:45678/hw/hw8/images/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_committee);

        Intent intent = getIntent();
         final CommitteeModel.ResultsBean committee_data = (CommitteeModel.ResultsBean)intent.getSerializableExtra(CommitteeHouseFragment.MESSAGE);
        TextView committeeid = (TextView) findViewById(R.id.committee_id);
        TextView committeename = (TextView) findViewById(R.id.committee_name);
        TextView committeechamber = (TextView) findViewById(R.id.committee_chamber);
        TextView parent_committee = (TextView) findViewById(R.id.parent_committee_name);
        TextView committeecontact = (TextView) findViewById(R.id.contact_no);
        TextView committeeoffice = (TextView) findViewById(R.id.office);
        ImageView chamberPhoto = (ImageView) findViewById(R.id.chamberImg);

        committeeid.setText(committee_data.getCommittee_id());
        committeename.setText(committee_data.getName());
        committeechamber.setText(committee_data.getChamber().substring(0,1).toUpperCase() + committee_data.getChamber().substring(1));
        parent_committee.setText(committee_data.getParent_committee_id()==null?"N.A" : committee_data.getParent_committee_id());
        committeecontact.setText(committee_data.getPhone()==null?"N.A":committee_data.getPhone());

        committeeoffice.setText(committee_data.getOffice()==null?"N.A" : committee_data.getOffice());
        String getChamber = committee_data.getChamber();
        if(getChamber.equals("house")){
                chamberurl += "h.png";
            Picasso.with(getApplicationContext()).load(chamberurl).into(chamberPhoto);
        }
        else{
            chamberPhoto.setImageResource(R.drawable.s);
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.comm_toolbar);
        myToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        final CheckBox fav_button =(CheckBox) findViewById(R.id.comm_fav_star);
        final boolean isFavourite = readState(committee_data.getCommittee_id());
        fav_button.setChecked(isFavourite);
        fav_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (isFavourite) {
                    saveState(committee_data, false);

                } else {

                    saveState(committee_data, true);

                }

            }
        });


    }

    private void saveState(CommitteeModel.ResultsBean committee_data, boolean isFavourite) {
        SharedPreferences committeFav = this.getSharedPreferences(
                "Committee_Favourite", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor aSharedPreferencesEdit = committeFav
                .edit();
        aSharedPreferencesEdit.putBoolean(committee_data.getCommittee_id(), isFavourite);

        aSharedPreferencesEdit.commit();

        SharedPreferences committeFavData = this.getSharedPreferences(
                "Committee_Favourite_Data", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = committeFavData.edit();

        if(isFavourite) {
            Gson gson = new Gson();
            String json = gson.toJson(committee_data);
            prefsEditor.putString(committee_data.getCommittee_id(), json);
        }
        else{
            prefsEditor.remove(committee_data.getCommittee_id());
        }
        prefsEditor.commit();

    }

    private boolean readState(String committeid) {
        SharedPreferences committeFav = this.getSharedPreferences(
                "Committee_Favourite", getApplicationContext().MODE_PRIVATE);
        return committeFav.getBoolean(committeid, false);
    }
}
