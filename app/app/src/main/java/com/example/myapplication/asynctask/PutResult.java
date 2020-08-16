package com.example.myapplication.asynctask;


import android.os.AsyncTask;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class PutResult extends AsyncTask<String, Integer, Object> {
    private boolean bonneReponse;
    private String id;
    private URL url;
    public boolean quizPersonnel = false;
    @Override
    protected Object doInBackground(String... strings) {
        try{
            if(!quizPersonnel) {
                if (bonneReponse) {
                    this.url = new URL("http://10.0.2.2:8080/api/residents/bonnereponse/" + this.id);
                } else {
                    this.url = new URL("http://10.0.2.2:8080/api/residents/mauvaisereponse/" + this.id);
                }
            }else{
                if (bonneReponse) {
                    this.url = new URL("http://10.0.2.2:8080/api/residents/ajouteBonneReponsePersonnel/" + this.id);
                } else {
                    this.url = new URL("http://10.0.2.2:8080/api/residents/ajouteMauvaiseReponsePersonnel/" + this.id);
                }
            }

            HttpURLConnection httpCon = (HttpURLConnection) this.url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");
            System.out.println("La réponse :"+httpCon.getResponseCode());

        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setBonneReponse(boolean bonneReponse) {
        this.bonneReponse = bonneReponse;
    }

    public void setId(String id){
        this.id = id;
    }
}
