package com.example.myapplication.collections;

import com.example.myapplication.models.QuizPersonal;

import java.util.ArrayList;

public class PersonalQuizCollection {
    public ArrayList<QuizPersonal> QuizArray; // all collective quiz are in here

    //constructor
    public PersonalQuizCollection() {
        QuizArray = new ArrayList<QuizPersonal>();
    }

    // adds a quiz to the collection
    public void addQuiz(QuizPersonal quiz){

        this.QuizArray.add(quiz);
    }

    public ArrayList<QuizPersonal> getQuizArray() {
        return QuizArray;
    }
}
