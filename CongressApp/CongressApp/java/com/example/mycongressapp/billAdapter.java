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

public class billAdapter extends ArrayAdapter<BillModel.ResultsBean> {

    private ArrayList<BillModel.ResultsBean> mbilllist;
    private LayoutInflater mInflater;
    private Context context;
    public billAdapter(Context context, ArrayList<BillModel.ResultsBean> billslist) {
        super(context,R.layout.bill_data_layout, billslist);

        mbilllist =billslist;
        mInflater= LayoutInflater.from(context);
        this.context = context;

    }
    @Override
    public int getCount(){
        return mbilllist.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BillModel.ResultsBean item = mbilllist.get(position);
        if (convertView == null) {
            convertView = mInflater.from(getContext()).inflate(R.layout.bill_items, parent, false);
        }


        TextView bill_Name = (TextView) convertView.findViewById(R.id.bill_name);
        TextView details = (TextView) convertView.findViewById(R.id.bill_details);
        String bill_date = item.getIntroduced_on();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = (Date)formatter.parse(bill_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("MMM dd, yyyy");
        bill_date = newFormat.format(date);


        bill_Name.setText(item.getBill_id().toUpperCase());
        details.setText((item.getShort_title()==null?item.getOfficial_title():item.getShort_title()) + "\n\n" + bill_date);
        return convertView;
    }

}