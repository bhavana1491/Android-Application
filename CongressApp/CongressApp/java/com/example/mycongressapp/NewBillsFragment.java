package com.example.mycongressapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bhavana on 11/21/16.
 */

public class NewBillsFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener{

    private ListView listView;
    private static String url = "http://sample-env.wwfa4myqwv.us-west-2.elasticbeanstalk.com/?category=bills&filter=New Bills";

    Map<String, Integer> billIndex;

    ArrayList<HashMap<String, String>> billList;
    BillModel bill_data;
    static List<BillModel.ResultsBean> results;
    View view;
    public static String MESSAGE="com.example.mycongressapp";
    LayoutInflater lf;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        billList = new ArrayList<>();
        View bill_view =inflater.inflate(R.layout.bill_layout,null);
        view = inflater.inflate(R.layout.bill_data_layout,null);

        listView = (ListView) view.findViewById(R.id.bills_list);
        listView.setOnItemClickListener(this);
        new GetBillInfo().execute();
        return view;

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(getActivity(),DisplayBillDetails.class);
        intent.putExtra(MESSAGE,(Serializable)results.get(position));
        startActivity(intent);
    }


    private class GetBillInfo extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... arg0) {
            httpReq sh = new httpReq();
            String jsonStr = sh.getUrlService(url);

            if (jsonStr != null) {
                try {

                    Gson gson = new Gson();


                    // Getting JSON Array node
                    jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
                    // jsonStr = jsonStr.replaceAll("\\\\","");
                    JsonReader jsonreader = new JsonReader(new StringReader(jsonStr));
                    jsonreader.setLenient(true);
                    bill_data = gson.fromJson(jsonreader, BillModel.class);
                    results = bill_data.getResults();
                }
                catch (Exception e) {
                    System.out.println("Json parsing error: " + e.getMessage());


                }
            }
            else{
                System.out.println("No Json Data");
            }


            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            ArrayList<BillModel.ResultsBean> billArray = new ArrayList<BillModel.ResultsBean>(results);

            billAdapter adapter = new billAdapter(getActivity(),billArray);

            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

    }
}

