package com.example.myapplication.collections;
import com.example.myapplication.models.Quiz;

import java.util.ArrayList; // import the ArrayList class

public class QuizCollection {

    public ArrayList<Quiz> QuizArray; // all collective quiz are in here

    //constructor
    public QuizCollection() {
        QuizArray = new ArrayList<Quiz>();
    }

    // adds a quiz to the collection
    public void addQuiz(Quiz quiz){

        this.QuizArray.add(quiz);
    }

    public ArrayList<Quiz> getQuizArray() {
        return QuizArray;
    }
}
