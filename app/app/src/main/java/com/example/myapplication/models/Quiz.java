package com.example.myapplication.models;

import com.example.myapplication.collections.QuestionCollection;

public class Quiz {
    public int id;
    public String Title;
    public String Description;
    public int numberQuestion;
    public QuestionCollection Questions;

    public Quiz(int id, String Title, String Description ,QuestionCollection questions){
        this.id = id;
        this.Title=Title;
        this.Description=Description;
        this.Questions=questions;
        this.numberQuestion=questions.questions.size();

    }

}
