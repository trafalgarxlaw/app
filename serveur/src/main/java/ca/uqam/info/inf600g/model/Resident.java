package ca.uqam.info.inf600g.model;

import ca.uqam.info.inf600g.data.QuizReponses;

import java.util.ArrayList;

public class Resident {
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private Resultat resultat;
    private PersonalQuizResults personalQuizResultsResults;
    public ArrayList<QuizReponses> answersList; // the list of the user answers per quizz
    private ArrayList<TremblingAvg> TremblingData;
    private boolean activerAdaptationAlzheimer;
    private boolean adaptationTailleTexte;
    private Resultat resultatQuizLongTerme;

    public Resident(){
        this.resultatQuizLongTerme = new Resultat();
        this.resultat = new Resultat();
    }
    public Resident(int id, String nom, String prenom, int age){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.resultat = new Resultat();
        this.personalQuizResultsResults=new PersonalQuizResults();
        this.answersList= new  ArrayList<QuizReponses>();
        this.TremblingData=new ArrayList<TremblingAvg>();
        initialiseUserAnswers();
        this.activerAdaptationAlzheimer = false;
        this.adaptationTailleTexte = false;
        this.resultatQuizLongTerme = new Resultat();
    }

    public void initialiseUserAnswers(){
        System.out.println("initialising quiz answer storage for "+this.nom);
        //initialisation for 10 Quizz
        for (int Quiz_id = 0; Quiz_id <3 ; Quiz_id++) {
            QuizReponses q_reponse = new QuizReponses(Quiz_id);
            this.answersList.add(q_reponse);
        }

    }

    public ArrayList<TremblingAvg> getTremblingData() {
        return TremblingData;
    }

    public void addTremblingData(float avg,float time) {
        TremblingAvg newavg= new TremblingAvg(avg,time);
        TremblingData.add(newavg);
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Resultat getResultat() {
        return resultat;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getLabel() {
        return Integer.toString(id);
    }

    public void ajouterBonneReponse(){
        this.resultat.ajouterBonneReponse();
    }

    public void ajouterMauvaiseReponse(){
        this.resultat.ajouterMauvaiseReponse();
    }

    public void setAnswer(String Quiz_id,String Question_id,String answer){
        int Quiz_id_parsed=Integer.parseInt(Quiz_id);
        int Question_id_parsed=Integer.parseInt(Question_id);

        int answer_parsed=Integer.parseInt(answer);;

        //creating the answer
        Reponse newAnswer= new Reponse(Question_id_parsed,answer_parsed);

        for (QuizReponses q_reponse:answersList) {

            // im looking for the quiz with this id
            if (q_reponse.getQuizId()==Quiz_id_parsed){

                //adding the answer to the array of answers for the quizz
                q_reponse.addAnswer(newAnswer);

            }
            
        }
    }

    public void ajouterBonneReponseQuizPersonnel(){
        this.resultatQuizLongTerme.ajouterBonneReponse();
    }

    public void ajouterMauvaiseReponseQuizPersonnel(){
        this.resultatQuizLongTerme.ajouterMauvaiseReponse();
    }

    public boolean getAdaptationAlzheimer(){
        return this.activerAdaptationAlzheimer;
    }

    public void modifierAdaptionAlzheimer(){
        this.activerAdaptationAlzheimer = !this.activerAdaptationAlzheimer;
    }

    public boolean getAdaptationTexte(){
        return this.adaptationTailleTexte;
    }

    public void modifierAdaptationTexte(){
        this.adaptationTailleTexte = ! this.adaptationTailleTexte;
    }

    public void updateGameScore(int newScore){
        this.personalQuizResultsResults.setMiniGame_Score(newScore);
    }

    public int getGameScore(){
        return this.personalQuizResultsResults.getMiniGame_Score();
    }

    public Resultat getResultatPersonnel(){
        return this.resultatQuizLongTerme;
    }
}

