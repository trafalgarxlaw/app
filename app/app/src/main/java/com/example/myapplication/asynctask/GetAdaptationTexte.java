package com.example.myapplication.asynctask;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetAdaptationTexte extends AsyncTask<Void, Void, Boolean>{
    private URL url;
    private int Id;
    private Boolean activerAdaptation = false;

    protected Boolean doInBackground(Void... params) {

        try {
            this.url = new URL("http://10.0.2.2:8080/api/residents/adaptationTailleTexte/"+this.Id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            Boolean json = new JSONObject().optBoolean(content.toString());
            System.out.println("LE GET ADAPTAITON TEXTE : "+ content.toString());
            return Boolean.parseBoolean(content.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setId(int id){
        this.Id = id;
    }

    public Boolean getAdaptationTexte(){
        return this.activerAdaptation;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
