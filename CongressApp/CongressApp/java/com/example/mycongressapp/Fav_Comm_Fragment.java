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

public class Fav_Comm_Fragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView listView;
    View view;
    CommitteeModel.ResultsBean committee_data;
    ArrayList<CommitteeModel.ResultsBean> results = new ArrayList<CommitteeModel.ResultsBean>();

    public static String MESSAGE="com.example.mycongressapp";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.committee_data_layout,null);

        listView = (ListView) view.findViewById(R.id.committee_list);
        listView.setOnItemClickListener(this);
        SharedPreferences committeFavData = this.getActivity().getSharedPreferences(
                "Committee_Favourite_Data", getActivity().MODE_PRIVATE);

        Map <String, ?> allkeys = committeFavData.getAll();
        for(Map.Entry<String, ?> entry : allkeys.entrySet()){
            Gson gson = new Gson();
            String json = committeFavData.getString(entry.getKey(),"");
            committee_data = gson.fromJson(json, CommitteeModel.ResultsBean.class );
            results.add(committee_data);
        }
        Collections.sort(results, new Comparator<CommitteeModel.ResultsBean>() {
            @Override
            public int compare(CommitteeModel.ResultsBean resultsBean, CommitteeModel.ResultsBean t1) {


                return resultsBean.getName().compareTo(t1.getName());
            }
        });

        Collections.sort(results);
        committeeAdapter adapter = new committeeAdapter(getActivity(),results);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(getActivity(),DisplayCommitteeDetails.class);
        intent.putExtra(MESSAGE,(Serializable)results.get(position));
        startActivity(intent);
    }
}
