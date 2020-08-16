package ca.uqam.info.inf600g.model;

public class Reponse {
    private int QuestionId; // the id of the quiz this answer is linked to.
    private int answer; // the answer choosen by the user for this answer

    public Reponse(int QuestionId,int answer){
        this.QuestionId=QuestionId;
        this.answer=answer;
    }

    public int getAnswer() {
        return answer;
    }

    public int getQuestionId() {
        return QuestionId;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public void setQuestionId(int questionId) {
        QuestionId = questionId;
    }
}

