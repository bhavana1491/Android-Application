package com.example.mycongressapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bhavana on 11/21/16.
 */

public class CommitteeSenateFragment extends Fragment implements AdapterView.OnItemClickListener{


    private ListView listView;
    private static String url = "http://sample-env.wwfa4myqwv.us-west-2.elasticbeanstalk.com/?category=committees&filter=Senate";

    Map<String, Integer> billIndex;

    ArrayList<HashMap<String, String>> committeeList;
    CommitteeModel committee_data;
    List<CommitteeModel.ResultsBean> results;
    View view;
    public static String MESSAGE="com.example.mycongressapp";
    LayoutInflater lf;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        committeeList = new ArrayList<>();
        View bill_view =inflater.inflate(R.layout.committee_layout,null);
        view = inflater.inflate(R.layout.committee_data_layout,null);

        listView = (ListView) view.findViewById(R.id.committee_list);
        listView.setOnItemClickListener(this);
        new CommitteeSenateFragment.GetCommitteeInfo().execute();
        return view;

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(getActivity(),DisplayCommitteeDetails.class);
        intent.putExtra(MESSAGE,(Serializable)results.get(position));
        startActivity(intent);
    }


    private class GetCommitteeInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            if(results==null) {
                httpReq sh = new httpReq();
                String jsonStr = sh.getUrlService(url);

                if (jsonStr != null) {
                    try {

                        Gson gson = new Gson();

                        jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
                        JsonReader jsonreader = new JsonReader(new StringReader(jsonStr));
                        jsonreader.setLenient(true);
                        committee_data = gson.fromJson(jsonreader, CommitteeModel.class);
                        results = committee_data.getResults();


                        Collections.sort(results, new Comparator<CommitteeModel.ResultsBean>() {
                            @Override
                            public int compare(CommitteeModel.ResultsBean resultsBean, CommitteeModel.ResultsBean t1) {


                                return resultsBean.getName().compareTo(t1.getName());
                            }
                        });

                        Collections.sort(results);

                    } catch (Exception e) {
                        System.out.println("Json parsing error: " + e.getMessage());


                    }
                } else {
                    System.out.println("No Json Data");
                }

            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ArrayList<CommitteeModel.ResultsBean> committeeArray = new ArrayList<CommitteeModel.ResultsBean>(results);
            committeeAdapter adapter = new committeeAdapter(getActivity(),committeeArray);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

    }
}