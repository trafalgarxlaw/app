package com.example.myapplication.asynctask;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetUsersNetwork extends AsyncTask<String, Integer, JSONArray> {
    static JSONArray jsonArray;

    public GetUsersNetwork(){

    }

    @Override
    protected JSONArray doInBackground(String... strings) {


        try {
            //Get the content from the server
            URL url = new URL("http://10.0.2.2:8080/api/residents");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            Gson gson = new Gson();
            jsonArray = new JSONArray(content.toString());



        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
    protected void onPostExecute(JSONArray r)
    {
        //onPostExecute
        super.onPostExecute(r);
    }

}
