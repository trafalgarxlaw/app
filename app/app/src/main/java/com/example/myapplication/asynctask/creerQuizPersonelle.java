package com.example.myapplication.asynctask;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.models.QuizPersonal;
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class creerQuizPersonelle extends AsyncTask<QuizPersonal, Void, Void> {
    public int id;
    public QuizPersonal quizPersonal;

    public void setId(int id){
        this.id = id;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Void doInBackground(QuizPersonal...params) {
        HttpURLConnection urlConnection;

        try {
            Gson gson = new Gson();

            String urlParameters  = gson.toJson(quizPersonal);
            System.out.println("LE QUIZ PERSONNEL:" + urlParameters);
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            String request        = "http://10.0.2.2:8080/api/quizz/ajouterQuiz/"+id;
            URL    url            = new URL( request );
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/json");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write( postData );
            }
            int responseCode = conn.getResponseCode();
            System.out.println("Le code de reposne est :" + responseCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
