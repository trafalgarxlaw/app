package com.example.myapplication.models;


import com.example.myapplication.asynctask.GetUsersNetwork;
import com.example.myapplication.datatypes.TremblingAverage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Resident implements Serializable {
    private int id;
    private String firstname;
    private String lastname;
    private int age;
    public UserResults results;
    public ArrayList<UserAnswers> answersList; // the list of the user answers per quizz
    private ArrayList<TremblingAverage> TremblingData;
    private boolean activerAdaptationAlzheimer;
    private boolean adaptationTailleTexte;

    public Resident(int id, String nom, String prenom, int age){
        this.id = id;
        this.lastname = nom;
        this.firstname = prenom;
        this.age = age;
        this.results = new UserResults();
        this.answersList=new ArrayList<UserAnswers>();
        this.TremblingData=new ArrayList<TremblingAverage>();
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setUserResults(UserResults newResults){
        this.results=newResults;
    }
    public void setanswersList(ArrayList<UserAnswers> answersList){
        this.answersList=answersList;
    }
    public void addToAnswersList(UserAnswers a){
        this.answersList.add(a);
    }

    public String getFirstname() {
        return firstname;
    }

    public void addCorrectAnswer(){
        this.results.addCorrectAnswer();
    }

    public void addwrongAnswer(){
        this.results.addwrongAnswer();
    }

    public double getResult(){
        return this.results.getResult();
    }

    public void addTremblingAverage(float avg, float time){
        TremblingAverage newAvg= new TremblingAverage(avg,time);
        this.TremblingData.add(newAvg);
    }

    public void fetchTremblingData()throws ExecutionException, InterruptedException, JSONException {

        this.TremblingData=new ArrayList<TremblingAverage>();

        JSONArray content= new GetUsersNetwork().execute().get();
        JSONObject ResidenJSON=null;

        for (int i = 0; i <content.length() ; i++) {
            JSONObject resident=content.getJSONObject(i);

            if (Integer.parseInt(resident.getString("id"))==this.id){
                ResidenJSON=resident;
                System.out.println("generateUserAnswers : "+ResidenJSON.toString());

            }

        }


        System.out.println("NAME : "+ ResidenJSON.getString("prenom"));
        //here i handle the answers of my user
        JSONArray tremblingDataArray = ResidenJSON.getJSONArray("tremblingData");
        System.out.println("tremblingData : "+tremblingDataArray);

        for (int i = 0; i <tremblingDataArray.length() ; i++) {
            JSONObject tremblingDataObject= tremblingDataArray.getJSONObject(i);
            float avg= (float) tremblingDataObject.getDouble("average");
            float time= (float) tremblingDataObject.getDouble("time");

            TremblingAverage newAvg= new TremblingAverage(avg,time);
            this.TremblingData.add(newAvg);
            System.out.println("average : "+avg+ ", time "+ time);

        }
    }
    public ArrayList<TremblingAverage>  getTremblingData(){
        return TremblingData;
    }

    public void generateUserAnswers() throws ExecutionException, InterruptedException, JSONException {
        JSONArray content= new GetUsersNetwork().execute().get();
        JSONObject ResidenJSON=null;

        for (int i = 0; i <content.length() ; i++) {
            JSONObject resident=content.getJSONObject(i);

            if (Integer.parseInt(resident.getString("id"))==this.id){
                ResidenJSON=resident;
                System.out.println("generateUserAnswers : "+ResidenJSON.toString());

            }

        }


        System.out.println("NAME : "+ ResidenJSON.getString("prenom"));
        //here i handle the answers of my user
        JSONArray jsonAnswersObject = ResidenJSON.getJSONArray("answersList");
        System.out.println("Les answers: "+jsonAnswersObject);


        for (int i = 0; i <jsonAnswersObject.length() ; i++) {


            JSONObject QuizAnsersObject= jsonAnswersObject.getJSONObject(i);
            int QuizID=QuizAnsersObject.getInt("quizId");
            JSONArray Jsonanswers = QuizAnsersObject.getJSONArray("answers");

            System.out.println("printing QuizAnsersObject "+QuizAnsersObject);
            System.out.println("printing answers "+Jsonanswers);

            ArrayList<Answer> Quizanswers = new ArrayList<Answer>();

//          looping through the answers of that quizz
            for (int j = 0; j <Jsonanswers.length() ; j++) {
                JSONObject answerObject= Jsonanswers.getJSONObject(j);

                int questionId= answerObject.getInt("questionId");
                int answer=answerObject.getInt("answer");

                Answer newAnswer= new Answer(questionId,answer);
                Quizanswers.add(newAnswer);
                System.out.println("printing Question id "+newAnswer.QuestionId);
                System.out.println("printing one answer "+newAnswer.answer);

            }

            UserAnswers userAnswers=new UserAnswers(QuizID,Quizanswers);
            addToAnswersList(userAnswers);


        }

    }

    public UserAnswers getAnswersByQuizId(int QuizId){
        UserAnswers a= null;

        for (int i = 0; i <answersList.size() ; i++) {
            if (answersList.get(i).getQuizId()==QuizId){
                a=answersList.get(i);
            }
        }
        return a;
    }
    public boolean UserAnswersIsEmpty(){
        return answersList.isEmpty() || answersList==null ;
    }
}

