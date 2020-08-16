package ca.uqam.info.inf600g.data;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ca.uqam.info.inf600g.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionCollection {

    public int getNumeroQuestion() {
        return numeroQuestion;
    }

    public void setNumeroQuestion(int numeroQuestion) {
        this.numeroQuestion = numeroQuestion;
    }

    public ArrayList<Question> getQuestionsArray() {
        return questions;
    }

    public void setQuestionsArray(ArrayList<Question> questionsArray) {
        this.questions = questionsArray;
    }
    @JsonProperty("numeroQuestion")
    private int numeroQuestion;
    @JsonProperty("questions")
    public ArrayList<Question> questions;

    public QuestionCollection() {
        this.questions = new ArrayList<>();
        this.numeroQuestion = 0;
    }

    public QuestionCollection(int numeroQuestion, ArrayList<Question> questions){
        this.questions = questions;
        this.numeroQuestion = numeroQuestion;
    }

    public Question getOneSpecificQuestion(int id){
        return this.questions.get(id);
    }


    public boolean containsKey(String id){
        return this.questions.contains (id);
    }

    public void addQuestion(Question question){
        this.questions.add(question);
        System.out.println(question.getQuestionSentence().toString());

    }

    @Override
    public String toString() {
        return "QuestionCollection{" +
                "numeroQuestion=" + numeroQuestion +
                ", questionsArray=" + questions.toString() +
                '}';
    }
}

