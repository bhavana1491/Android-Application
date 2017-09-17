package com.example.mycongressapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bhavana on 11/24/16.
 */

public class Fav_Leg_Fragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView listView;
    View view;
    LegislatorModel.ResultsBean leg_data;
    ArrayList<LegislatorModel.ResultsBean> results = new ArrayList<LegislatorModel.ResultsBean>();

    public static String MESSAGE="com.example.mycongressapp";

    LinkedHashMap<String, Integer> nameIndex;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.by_state_layout,null);

        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        SharedPreferences billFavData = this.getActivity().getSharedPreferences(
                "Leg_Favourite_Data", getActivity().MODE_PRIVATE);

        Map <String, ?> allkeys = billFavData.getAll();
        for(Map.Entry<String, ?> entry : allkeys.entrySet()){
            Gson gson = new Gson();
            String json = billFavData.getString(entry.getKey(),"");
            leg_data = gson.fromJson(json, LegislatorModel.ResultsBean.class );
            results.add(leg_data);
        }
        Collections.sort(results, new Comparator<LegislatorModel.ResultsBean>() {
            @Override
            public int compare(LegislatorModel.ResultsBean resultsBean, LegislatorModel.ResultsBean t1) {


                return resultsBean.getLast_name().compareTo(t1.getLast_name());
            }
        });


        Collections.sort(results);
        legislatorAdapter adapter = new legislatorAdapter(getActivity(),results);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getIndexList(results);
        displayIndex();
        return view;

    }

    private void getIndexList(List<LegislatorModel.ResultsBean> results) {
        nameIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < results.size(); i++) {
            String last_name = results.get(i).getLast_name();
            String index = last_name.substring(0, 1);

            if (nameIndex.get(index) == null)
                nameIndex.put(index, i);
        }
    }

    private void displayIndex() {
        LinearLayout indexLayout = (LinearLayout) view.findViewById(R.id.side_index);

        TextView textView;
        List<String> indexList = new ArrayList<String>(nameIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.side_index, null);
            textView.setText(index);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView selectedIndex = (TextView) view;
                    listView.setSelection(nameIndex.get(selectedIndex.getText()));
                }
            });
            indexLayout.addView(textView);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(getActivity(),DisplayLegislatorDetails.class);
        intent.putExtra(MESSAGE,(Serializable)results.get(position));
        startActivity(intent);
    }
}

