package ca.uqam.info.inf600g.data;

import ca.uqam.info.inf600g.model.Reponse;

import java.util.ArrayList;

public class QuizReponses {
    private int QuizId; // the id of the quiz these results are linked to.
    private int numberQuizQuestions;
    public ArrayList<Reponse> answers; // the list of the user answers per question


    public QuizReponses(int QuizId){
        this.QuizId=QuizId;
        this.numberQuizQuestions=numberQuizQuestions;
        this.answers=new ArrayList<Reponse>();
    }




    public ArrayList<Reponse> getAnswers() {
        return answers;
    }

    public int getQuizId() {
        return QuizId;
    }

    public void setQuizId(int quizId) {
        QuizId = quizId;
    }

    public void addAnswer(Reponse r){
        answers.add(r);
    }


}
