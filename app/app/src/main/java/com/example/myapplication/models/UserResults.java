package com.example.myapplication.models;
// One User can have multiple UserResults depending on how many Quizz they have played
public class UserResults {
    private int questionAnswered;
    private int correctAnswers;
    private int wrongAnswers;

    public UserResults(){
        this.questionAnswered = 0;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
    }
    public UserResults(int questionAnswered,int correctAnswers,int wrongAnswers){
        this.questionAnswered = questionAnswered;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
    }

    public void addCorrectAnswer(){
        this.questionAnswered += 1;
        this.correctAnswers += 1;
    }

    public void addwrongAnswer(){
        this.questionAnswered += 1;
        this.wrongAnswers += 1;
    }

    public double getResult() {
        if(this.questionAnswered != 0) {
            return Double.valueOf(this.correctAnswers) / Double.valueOf(this.questionAnswered);
        }else {
            return 0;
        }
    }

    public int getcorrectAnswers() {
        return this.correctAnswers;
    }

    public int getWrongAnswers() {
        return this.wrongAnswers;
    }

    public int getQuestionAnswered(){
        return this.questionAnswered;
    }

}

