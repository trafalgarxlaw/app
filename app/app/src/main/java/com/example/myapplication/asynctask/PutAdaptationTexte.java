package com.example.myapplication.asynctask;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class PutAdaptationTexte extends AsyncTask {
    private URL url;
    private int id;
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            this.url = new URL("http://10.0.2.2:8080/api/residents/adaptationTailleTexte/"+this.id);
            HttpURLConnection httpCon = (HttpURLConnection) this.url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");
            System.out.println("La réponse :"+httpCon.getResponseCode());
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setId(int id){
        this.id = id;
    }
}
