package com.example.myapplication.collections;


import com.example.myapplication.models.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class QuestionCollection implements Serializable {

    private int numeroQuestion;
    //public ArrayList<Question> questions;
    public ArrayList<Question> questions; // all Questions of a specific quiz are stored here

    public QuestionCollection() {
        //this.questions = new ArrayList<>();
        this.questions = new ArrayList<Question>();
        this.numeroQuestion = 0;
    }


    public Set<Question> getAllQuestions(){
        return new HashSet<>(this.questions);
    }

    public Question getOneSpecificQuestion(int id){
        return this.questions.get(id);
    }

    public Question getNextQuestion() throws CloneNotSupportedException {
        Question questionRetournee = (Question) this.questions.get(numeroQuestion).Clone();
        this.numeroQuestion = (++this.numeroQuestion)%this.questions.size();
        System.out.println("Le numero de question : "+this.numeroQuestion);
        return questionRetournee;
    }

    public boolean containsKey(String id){
        return this.questions.contains (id);
    }

    public void addQuestion(Question question){
        this.questions.add(question);
        System.out.println(question.getQuestion().toString());

    }

    public void addQuestionArray(Question question){
        this.questions.add(question);
    }

    @Override
    public String toString() {
        return "{" +
                "numeroQuestion=" + numeroQuestion +
                ", questionsArray=" + questions.toString() +
                '}';
    }

    public int getSize(){
        return this.questions.size();
    }
}
