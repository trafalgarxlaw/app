package com.example.myapplication.asynctask;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.collections.QuestionCollection;
import com.example.myapplication.collections.QuizCollection;
import com.example.myapplication.models.Question;
import com.example.myapplication.models.Quiz;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Network extends AsyncTask<String, Integer, QuizCollection> {
    public QuizCollection quizz;

    static InputStream is = null;

    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public Network() {

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected QuizCollection doInBackground(String... params) {

        try {
            // On doit utiliser cet adresse URL, le 127.0.0.1 ne marche pas a cause du serveur qui
            // Roule deja sur l'adresse.

            //Get the content from the server
            URL url = new URL("http://10.0.2.2:8080/api/quizz");
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
            JSONArray jsonArray = new JSONArray(content.toString());
            System.out.println("Le content : "+content.toString());




            quizz = new QuizCollection();
            for (int i=0; i<jsonArray.length();i++){
//                System.out.println("Getting specific field: "+jsonArray.getJSONObject(i).getString("questions"));
                System.out.println("Getting entire json: "+jsonArray.getJSONObject(i));

                JSONObject jsonObject=jsonArray.getJSONObject(i).getJSONObject("questions");
                ArrayList<Question> questionsArrayData= generateQuestionsArrayFromJson(jsonObject);


                QuestionCollection collection=new QuestionCollection();
                collection.questions=questionsArrayData;


                //Parsing some info about the Quiz
                int Quizid= jsonArray.getJSONObject(i).getInt("id");
                String QuizTitile= jsonArray.getJSONObject(i).getString("Title");
                String QuizDescription= jsonArray.getJSONObject(i).getString("Description");

                Quiz quiz=new Quiz(Quizid,QuizTitile,QuizDescription,collection);
                System.out.println("Titre "+quiz.Title);
                quizz.addQuiz(quiz);
            }



            in.close();
            return quizz;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected void onPostExecute(QuizCollection result)
    {
        //onPostExecute
        super.onPostExecute(result);
    }
    protected  ArrayList<Question> generateQuestionsArrayFromJson(JSONObject jsonObject) throws JSONException {

        ArrayList<Question> listdata = new ArrayList<Question>();
        System.out.println("here is my object: "+jsonObject);
        JSONArray jArray = jsonObject.getJSONArray("questionsArray");
        System.out.println("here is my array: "+jArray);
        for (int i = 0; i <jArray.length() ; i++) {
            if (jArray != null) {
                JSONObject dataObj = (JSONObject) jArray.get(i);
                String qstid = dataObj.getString("id");
                String qstSentence= dataObj.getString("questionSentence");
                String choix1= dataObj.getString("choixUn");;
                String choix2= dataObj.getString("choixDeux");;
                String choix3= dataObj.getString("choixTrois");;
                String answer= dataObj.getString("reponse");;


                System.out.println("here is my string id index i: "+qstid);
                System.out.println("here is my sentence id index i: "+qstSentence);

                Question qst= new Question(qstid,qstSentence,choix1,choix2,choix3,answer);
                listdata.add(qst);
            }
        }

        System.out.println("HEREEEEEEE");

        System.out.println("blabla"+listdata.get(0).questionSentence);
        return listdata;
    }


}


