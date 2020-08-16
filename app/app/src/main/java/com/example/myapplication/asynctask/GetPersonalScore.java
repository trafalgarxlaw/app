package com.example.myapplication.asynctask;

import android.os.AsyncTask;

import com.example.myapplication.models.Resultat;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetPersonalScore extends AsyncTask<Void, Void, Object> {
    public String id;
    @Override
    protected Resultat doInBackground(Void... voids) {
        try {
            //Get the content from the server
            URL url = new URL("http://10.0.2.2:8080/api/residents/resultatPersonelle/"+id);
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
            Resultat resultat = gson.fromJson(content.toString(),Resultat.class);
            System.out.println("\n LE RESULTAT: " + resultat);
            return resultat;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
