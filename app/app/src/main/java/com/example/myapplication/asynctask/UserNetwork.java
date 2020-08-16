package com.example.myapplication.asynctask;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.collections.QuizCollection;
import com.example.myapplication.collections.ResidentsCollection;
import com.example.myapplication.models.Resident;
import com.example.myapplication.models.UserResults;

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

public class UserNetwork extends AsyncTask<String, Integer, ResidentsCollection> {
    public QuizCollection quizz;

    static InputStream is = null;

    static JSONObject jObj = null;
    static String json = "";


    static JSONArray jsonArray;
    static ResidentsCollection residents;
    // constructor
    public UserNetwork() {

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected ResidentsCollection doInBackground(String... params) {

        try {
            //Get the content from the server
            URL url = new URL("http://10.0.2.2:8080/api/residents");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            jsonArray = new JSONArray(content.toString());
            System.out.println("Le content : "+content.toString());



            //creating the collection of residents
            residents= new ResidentsCollection();


            for (int i=0; i<jsonArray.length();i++){
                System.out.println("Getting entire json: "+jsonArray.getJSONObject(i));
                Resident newResident = generateResidentsFromJson(jsonArray.getJSONObject(i));
                residents.addResident(newResident);
            }



            in.close();
            return residents;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected void onPostExecute(ResidentsCollection residents)
    {
        //onPostExecute
        super.onPostExecute(residents);
    }
    protected  Resident generateResidentsFromJson(JSONObject jsonObject) throws JSONException {

        int ResidentId=jsonObject.getInt("id");
        String Firstname=jsonObject.getString("prenom");
        String Lastname=jsonObject.getString("nom");
        int age =jsonObject.getInt("age");
        Resident newResident = new  Resident(ResidentId,Lastname,Firstname,age);


        //here i handle the results of my user
        JSONObject jsonResultsObject=jsonObject.getJSONObject("resultat");
        System.out.println("Les res: "+jsonResultsObject);

        int answeredQuestions=jsonResultsObject.getInt("questionRepondu");
        int correctQuestions=jsonResultsObject.getInt("bonneReponse");
        int wrongAnswers=jsonResultsObject.getInt("mauvaiseReponse");


        //creating the userResults
        UserResults results=new UserResults(answeredQuestions,correctQuestions,wrongAnswers);

        newResident.setUserResults(results);

        return newResident;
    }


}


