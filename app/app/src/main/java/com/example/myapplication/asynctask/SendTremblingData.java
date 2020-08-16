package com.example.myapplication.asynctask;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendTremblingData extends AsyncTask<String, Integer, Object> {
    private int id;
    private float avg;
    private float time;
    private URL url;

    @Override
    protected Object doInBackground(String... strings) {
        try{
            this.url = new URL("http://10.0.2.2:8080/api/residents/tremblements/"+this.id+"/"+avg+"/"+time);


            HttpURLConnection httpCon = (HttpURLConnection) this.url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");
            System.out.println("http://10.0.2.2:8080/api/residents/tremblements/"+this.id+"/"+avg+"/"+time);
            System.out.println("id :"+this.id);
            System.out.println("avg :"+this.avg);
            System.out.println("time :"+this.time);

            System.out.println("getResponseCode :"+httpCon.getResponseCode());

        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
