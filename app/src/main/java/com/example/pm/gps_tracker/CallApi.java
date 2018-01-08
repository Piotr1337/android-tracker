package com.example.pm.gps_tracker;


import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by PM on 08.01.2018.
 */

public class CallApi extends AsyncTask<String, Void, String> {
    private String Error = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        BufferedReader reader=null;

        try{
            URL url = new URL(params[0]);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("LatLngJson", params[1]);
            String query = builder.build().getEncodedQuery();
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            conn.connect();

        } catch (Exception ex) {
            Error = ex.getMessage();
        }
        finally {
            try
            {
                reader.close();
            }
            catch(Exception ex) {}
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    public String formatLatLngDataAsJSON(Double lat, Double lng) {
        final JSONObject root = new JSONObject();
        try{
            root.put("Lat", lat.toString());
            root.put("Lng", lng.toString());

            return root.toString();
        } catch (JSONException e1) {
            Log.d("Jwp","Nie można sformatować na JSON");
        }
        return null;
    }
}
