package com.example.myapplication.models;

import java.util.ArrayList;

// this class represents the answers given by the User for ONE quizz
public class UserAnswers {
    private int QuizId; // the id of the quiz these results are linked to.
    public ArrayList<Answer> answers; // the list of the user answers per question

    public UserAnswers(int QuizId,ArrayList<Answer> answers){
        this.QuizId=QuizId;
        this.answers=answers;
    }

    public int getQuizId() {
        return QuizId;
    }

    public Answer getAnswerByQuestionId(int questionId){
        Answer a=null;
        for (int i = 0; i <answers.size() ; i++) {
            if (answers.get(i).QuestionId==questionId){
                a=answers.get(i);
            }
        }
        return a;
    }
}
