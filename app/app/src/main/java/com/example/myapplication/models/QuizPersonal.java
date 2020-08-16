package com.example.myapplication.models;

import com.example.myapplication.collections.QuestionCollection;

import java.io.Serializable;

public class QuizPersonal implements Serializable {
    public int id;
    public String Title;
    public String residentName;
    public String Description;
    public int numberQuestion;

    public QuestionCollection getQuestions() {
        return questions;
    }

    public void setQuestions(QuestionCollection questions) {
        questions = questions;
    }

    public QuestionCollection questions;

    public int getResidentID() {
        return residentID;
    }

    public void setResidentID(int residentID) {
        this.residentID = residentID;
    }

    private int residentID;

    public QuizPersonal(int id, String Title, String Description ,QuestionCollection questions){
        this.id = id;
        this.Title=Title;
        this.Description=Description;
        this.questions=questions;
        this.numberQuestion=questions.questions.size();
        this.residentID = 0;
    }

    public QuizPersonal(int id, String Title, String Description ,QuestionCollection questions, int residentID){
        this.id = id;
        this.Title=Title;
        this.Description=Description;
        this.questions=questions;
        this.numberQuestion=questions.questions.size();
        this.residentID = residentID;
    }
    public QuizPersonal(int residentID, String residentName){
        this.id = 0;
        this.Title="";
        this.Description="";
        this.residentName = residentName;
        this.questions = new QuestionCollection();
        this.numberQuestion = 0;
        this.residentID = residentID;
    }

    public void ajouterQuestion(Question question){
        this.questions.addQuestionArray(question);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", Title='" + Title + '\'' +
                ", residentName='" + residentName + '\'' +
                ", Description='" + Description + '\'' +
                ", numberQuestion=" + numberQuestion +
                ", questions=" + questions.toString() +
                ", residentID=" + residentID +
                '}';
    }
}
