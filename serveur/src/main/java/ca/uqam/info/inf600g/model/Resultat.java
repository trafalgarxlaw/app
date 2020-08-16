package ca.uqam.info.inf600g.model;

public class Resultat {
    private int questionRepondu;
    private int BonneReponse;
    private int mauvaiseReponse;

    public Resultat(){
        this.questionRepondu = 0;
        this.BonneReponse = 0;
        this.mauvaiseReponse = 0;
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

}
