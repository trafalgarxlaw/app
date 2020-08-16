package com.example.myapplication.asynctask;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class getMiniGameScore extends AsyncTask<String, Integer, Float> {

    private String user_id;
    private float user_score;

    public getMiniGameScore(String user_id){
        this.user_id=user_id;

    }

    @Override
    protected Float doInBackground(String... strings) {


        try {
            //Get the content from the server
            URL url = new URL("http://10.0.2.2:8080/api/residents/miniGame_Score/"+user_id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            System.out.println("getResponseCode Score:"+con.getResponseCode());
            System.out.println("inputStream Score:"+content.toString());

            this.user_score=Float.parseFloat(content.toString());




        } catch (Exception e) {
            e.printStackTrace();
        }
        return user_score;
    }
    protected void onPostExecute(Float user_score)
    {
        //onPostExecute
        super.onPostExecute(user_score);
    }

}