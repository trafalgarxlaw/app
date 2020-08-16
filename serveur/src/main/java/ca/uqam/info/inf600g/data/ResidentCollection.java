package ca.uqam.info.inf600g.data;

import ca.uqam.info.inf600g.model.Resident;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ResidentCollection {
    private static ResidentCollection database;

    public static ResidentCollection getAccess(){
        if(database==null)
            database = new ResidentCollection();
        return database;
    }

    private Map<String, Resident> resident;
    private ResidentCollection() {
        this.resident = new HashMap<>();
        initialize();
    }

    private void initialize(){

        Resident resident0 = new Resident(0, "Travis", "Fred", 73);
        Resident resident1 = new Resident(1, "lavada", "Camille", 31);
        Resident resident2 = new Resident(2, "Neil", "Maxime", 45);
        Resident resident3 = new Resident(3, "Nevada", "Xavier", 90);


        this.resident.put(resident0.getLabel(), resident0);
        this.resident.put(resident1.getLabel(), resident1);
        this.resident.put(resident2.getLabel(), resident2);
        this.resident.put(resident3.getLabel(), resident3);

    }

    public Set<Resident> getAllResident(){
        return new HashSet<>(this.resident.values());
    }

    public void ajouterBonneReponse(String id){
        if (this.resident.containsKey(id)) {
            this.resident.get(id).ajouterBonneReponse();
        }
    }

    public void ajouterMauvaiseReponse(String id){
        if (this.resident.containsKey(id)) {
            this.resident.get(id).ajouterMauvaiseReponse();
        }
    }

    public void addAnswerToQuiz(String user_ID,String Quiz_id,String Question_id,String answer){
      this.resident.get(user_ID).setAnswer(Quiz_id,Question_id,answer);
    }

    public boolean containsKey(String id){
        return this.resident.containsKey(id);
    }

    public Resident getOneResident(String id){
        return this.resident.get(id);
    }

    public void addTremblingData(String user_ID,String avg,String temps){

        this.resident.get(user_ID).addTremblingData(Float.parseFloat(avg),Float.parseFloat(temps));
    }

    public void updateUserGameScore(String user_ID,int score){
        this.resident.get(user_ID).updateGameScore(score);
    }
    public int getUserGameScore(String user_ID){
        return this.resident.get(user_ID).getGameScore();
    }

    public void ajouterBonneReponseQuizPersonnel(String id){
        if (this.resident.containsKey(id)) {
            this.resident.get(id).ajouterBonneReponseQuizPersonnel();
        }
    }

    public void ajouterMauvaiseReponseQuizPersonnel(String id){
        if (this.resident.containsKey(id)) {
            this.resident.get(id).ajouterMauvaiseReponseQuizPersonnel();
        }
    }

}
