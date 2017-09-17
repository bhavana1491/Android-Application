package com.example.mycongressapp;

/**
 * Created by Bhavana on 11/19/16.
 */
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;



public class httpReq {
    private static final String TAG = httpReq.class.getSimpleName();

    public httpReq() {
    }

    public String getUrlService(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream input = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(input);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream instr) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(instr));
        StringBuilder str = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                str.append(line).append('\n');
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                instr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str.toString();
    }
}
