package com.example.myapplication.asynctask;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetAdaptationAlzheimer extends AsyncTask<Void, Void, Boolean> {
    private URL url;
    private int Id;
    private Boolean activerAdaptationAlzheimer = false;

    protected Boolean doInBackground(Void... params) {

        try {
            this.url = new URL("http://10.0.2.2:8080/api/residents/adaptationAlzheimer/"+this.Id);
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
            return Boolean.parseBoolean(content.toString());
            //System.out.println("LA VALEUR initialiser dans le GETADAPTATION : " + this.activerAdaptationAlzheimer);
         //   return Boolean.parseBoolean(content.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setId(int id){
        this.Id = id;
        System.out.println("\nLE ID EST : " + id);
    }

    public Boolean getActiverAdaptationAlzheimer(){
        System.out.println("\n LE RETOUR DANS LE GET JAVA:  "+ this.activerAdaptationAlzheimer+ "\n ");
        return this.activerAdaptationAlzheimer;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
