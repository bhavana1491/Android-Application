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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.Serializable;
import java.io.StringReader;
import java.util.*;


public class ByStateFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView listView;
    private static String url = "http://sample-env.wwfa4myqwv.us-west-2.elasticbeanstalk.com?category=legislators";

    Map<String, Integer> stateIndex;

    ArrayList<HashMap<String, String>> statetList;
    LegislatorModel leg_data;
    List<LegislatorModel.ResultsBean> results;
    View view;
    public static String MESSAGE="com.example.mycongressapp";
    LayoutInflater lf;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        statetList = new ArrayList<>();
        View lv =inflater.inflate(R.layout.leg_layout,null);
        view = inflater.inflate(R.layout.by_state_layout,null);
        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        new GetLegInfo().execute();
        return view;

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(getActivity(),DisplayLegislatorDetails.class);
        intent.putExtra(MESSAGE,(Serializable)results.get(position));
        startActivity(intent);
    }


    private class GetLegInfo extends AsyncTask<Void, Void, Void> {


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
                        leg_data = gson.fromJson(jsonreader, LegislatorModel.class);
                        results = leg_data.getResults();


                    } catch (Exception e) {
                        System.out.println("Json parsing error: " + e.getMessage());


                    }
                } else {
                    System.out.println("No Json Data");
                }

            }
            return null;
        }
        private void getIndexList(List<LegislatorModel.ResultsBean> results) {
            stateIndex = new LinkedHashMap<String, Integer>();
            for (int i = 0; i < results.size(); i++) {
                String state_name = results.get(i).getState_name();
                String index = state_name.substring(0, 1);

                if (stateIndex.get(index) == null)
                    stateIndex.put(index, i);
            }
        }

        private void displayIndex() {
            LinearLayout indexLayout = (LinearLayout) view.findViewById(R.id.side_index);

            TextView textView;
            List<String> indexList = new ArrayList<String>(stateIndex.keySet());
            for (String index : indexList) {
                textView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.side_index, null);
                textView.setText(index);
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView selectedIndex = (TextView) view;
                        listView.setSelection(stateIndex.get(selectedIndex.getText()));
                    }
                });
                indexLayout.addView(textView);
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ArrayList<LegislatorModel.ResultsBean> legArray = new ArrayList<LegislatorModel.ResultsBean>(results);
             legislatorAdapter adapter = new legislatorAdapter(getActivity(),legArray);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            getIndexList(results);
            displayIndex();
        }

    }
}
