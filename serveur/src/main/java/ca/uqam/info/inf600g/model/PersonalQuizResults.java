package ca.uqam.info.inf600g.model;

public class PersonalQuizResults {
    private int questionRepondu;
    private int BonneReponse;
    private int mauvaiseReponse;
    private int miniGame_Score;

    public PersonalQuizResults(){
        this.questionRepondu = 0;
        this.BonneReponse = 0;
        this.mauvaiseReponse = 0;
        this.miniGame_Score=0;
    }

    public void ajouterBonneReponse(){
        this.questionRepondu += 1;
        this.BonneReponse += 1;
    }

    public void ajouterMauvaiseReponse(){
        this.questionRepondu += 1;
        this.mauvaiseReponse += 1;
    }

    public double getResultat() {
        if(this.questionRepondu != 0) {
            return Double.valueOf(this.BonneReponse) / Double.valueOf(this.questionRepondu);
        }else {
            return 0;
        }
    }

    public int getBonneReponse() {
        return BonneReponse;
    }

    public int getMauvaiseReponse() {
        return mauvaiseReponse;
    }

    public int getQuestionRepondu(){
        return this.questionRepondu;
    }

    public void setMiniGame_Score(int miniGame_Score) {
        this.miniGame_Score = miniGame_Score;
    }

    public int getMiniGame_Score() {
        return miniGame_Score;
    }
}

