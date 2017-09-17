package com.example.mycongressapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.mycongressapp.R.id.imageView;

/**
 * Created by Bhavana on 11/20/16.
 */

public class committeeAdapter extends ArrayAdapter<CommitteeModel.ResultsBean> {

    private ArrayList<CommitteeModel.ResultsBean> mcommitteelist;
    private LayoutInflater mInflater;
    private Context context;
    public committeeAdapter(Context context, ArrayList<CommitteeModel.ResultsBean> committeelist) {
        super(context,R.layout.committee_data_layout, committeelist);

        mcommitteelist =committeelist;
        mInflater= LayoutInflater.from(context);
        this.context = context;

    }
    @Override
    public int getCount(){
        return mcommitteelist.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommitteeModel.ResultsBean item = mcommitteelist.get(position);

        if (convertView == null) {
            convertView = mInflater.from(getContext()).inflate(R.layout.committee_items, parent, false);
        }
        TextView committee_Name = (TextView) convertView.findViewById(R.id.committee_name);
        TextView details = (TextView) convertView.findViewById(R.id.committee_details);
        committee_Name.setText(item.getCommittee_id().toUpperCase());

        String chamber = item.getChamber().substring(0, 1).toUpperCase() + item.getChamber().substring(1);
        details.setText(item.getName() + "\n\n" + chamber);

        return convertView;
    }

}
