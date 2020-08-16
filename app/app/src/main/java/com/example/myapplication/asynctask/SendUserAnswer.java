package com.example.myapplication.asynctask;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendUserAnswer extends AsyncTask<String, Integer, Object> {
    private URL url;
    private int user_id;
    private int Quiz_id;
    private int Question_id;
    private int answer;


    @Override
    protected Object doInBackground(String... strings) {
        try{
            this.url = new URL("http://10.0.2.2:8080/api/residents/reponse/"+user_id+"/"+Quiz_id+"/"+Question_id+"/"+answer);


            HttpURLConnection httpCon = (HttpURLConnection) this.url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");

            System.out.println("getResponseCode for sending answer :"+httpCon.getResponseCode());

        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setQuiz_id(int quiz_id) {
        Quiz_id = quiz_id;
    }

    public void setQuestion_id(int question_id) {
        Question_id = question_id;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
