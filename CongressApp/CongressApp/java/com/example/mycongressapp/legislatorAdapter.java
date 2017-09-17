package com.example.mycongressapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.*;

/**
 * Created by Bhavana on 11/20/16.
 */

public class legislatorAdapter extends ArrayAdapter<LegislatorModel.ResultsBean> {

    private ArrayList<LegislatorModel.ResultsBean> mlegislatorslist;
    private LayoutInflater mInflater;
    private Context context;
    public legislatorAdapter(Context context, ArrayList<LegislatorModel.ResultsBean> legislatorslist) {
        super(context,R.layout.by_state_layout, legislatorslist);

        mlegislatorslist =legislatorslist;
        mInflater= LayoutInflater.from(context);
        this.context = context;

    }
    @Override
    public int getCount(){
        return mlegislatorslist.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LegislatorModel.ResultsBean item = mlegislatorslist.get(position);
        String photourl = "";

       if (convertView == null) {
            convertView = mInflater.from(getContext()).inflate(R.layout.leg_items, parent, false);
        }


        // Lookup view for data population
        TextView last_Name = (TextView) convertView.findViewById(R.id.name);
        TextView details = (TextView) convertView.findViewById(R.id.details);
        ImageView legPhoto = (ImageView) convertView.findViewById(R.id.legImg);
        photourl = "http://theunitedstates.io/images/congress/225x275/" + item.getBioguide_id() + ".jpg";
        last_Name.setText(item.getLast_name() + ", " + item.getFirst_name() );
        details.setText("(" + item.getParty() + ")" + item.getState_name() + " - " + "District" + " " +item.getDistrict());

        Picasso.with(context).load(photourl).into(legPhoto);
        return convertView;
    }

}
