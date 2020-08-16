package com.example.myapplication.models;

public class Answer {
    public int QuestionId=-1; // the id of the quiz this answer is linked to.
    public int answer=-1; // the answer choosen by the user for this answer

    public Answer(int QuestionId,int answer)
    {
        this.QuestionId=QuestionId;
        this.answer=answer;
    }

    public String getAnswer(){
        String a= " ";
        if (answer==-1){
            a="pas repondue";
        }else {
            a=String.valueOf(answer);
        }

        return a;

    }

}
