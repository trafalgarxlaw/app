package com.example.myapplication.asynctask;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class updateMiniGameScore extends AsyncTask<String, Integer, Object> {
    private int id;
    private int score;
    private URL url;

    @Override
    protected Object doInBackground(String... strings) {
        try{
            this.url = new URL("http://10.0.2.2:8080/api/residents/miniGame_Score/"+this.id+"/"+score);


            HttpURLConnection httpCon = (HttpURLConnection) this.url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");
            System.out.println("id :"+this.id);
            System.out.println("score :"+this.score);

            System.out.println("getResponseCode :"+httpCon.getResponseCode());

        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setId(int id) {
        this.id = id;
    }
}
