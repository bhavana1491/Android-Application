package com.example.mycongressapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Bhavana on 11/24/16.
 */

public class Fav_Bills_Fragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView listView;
    View view;
    BillModel.ResultsBean bill_data;
    ArrayList<BillModel.ResultsBean> results = new ArrayList<BillModel.ResultsBean>();

    public static String MESSAGE="com.example.mycongressapp";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bill_data_layout,null);

        listView = (ListView) view.findViewById(R.id.bills_list);
        listView.setOnItemClickListener(this);
        SharedPreferences billFavData = this.getActivity().getSharedPreferences(
                "Bill_Favourite_Data", getActivity().MODE_PRIVATE);

        Map <String, ?> allkeys = billFavData.getAll();
        for(Map.Entry<String, ?> entry : allkeys.entrySet()){
            Gson gson = new Gson();
            String json = billFavData.getString(entry.getKey(),"");
            bill_data = gson.fromJson(json, BillModel.ResultsBean.class );
            results.add(bill_data);
        }

        Collections.sort(results, new Comparator<BillModel.ResultsBean>() {
            @Override
            public int compare(BillModel.ResultsBean resultsBean, BillModel.ResultsBean t1) {


                return resultsBean.getIntroduced_on().compareTo(t1.getIntroduced_on());
            }
        });


        Collections.sort(results);
        Collections.reverse(results);
        billAdapter adapter = new billAdapter(getActivity(),results);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(getActivity(),DisplayBillDetails.class);
        //Bundle bundle = new Bundle();
        //bundle.putSerializable(MESSAGE,results.get(position).getCommittee_id());
        intent.putExtra(MESSAGE,(Serializable)results.get(position));
        startActivity(intent);
    }
}
