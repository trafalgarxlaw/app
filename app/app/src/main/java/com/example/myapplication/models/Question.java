package com.example.myapplication.models;

import java.io.Serializable;

public class Question implements Serializable, Cloneable {

    public int id;
    private String choixUn;
    private String choixDeux;
    private String choixTrois;
    private String reponse;
    public String questionSentence;
    public String media;

    public Question(String id, String question, String choix_un, String choix_deux, String choix_trois, String bonne_reponse) {
        this.id = Integer.parseInt(id);
        this.questionSentence = question;
        this.choixUn = choix_un;
        this.choixDeux = choix_deux;
        this.choixTrois = choix_trois;
        this.reponse = bonne_reponse;
        // Lorsque c'est une quesiton Normale, le fichier de média est null.
        this.media = null;
    }

    public Question(String id, String question, String choix_un, String choix_deux, String choix_trois, String bonne_reponse, String media) {
        this.id = Integer.parseInt(id);
        this.questionSentence = question;
        this.choixUn = choix_un;
        this.choixDeux = choix_deux;
        this.choixTrois = choix_trois;
        this.reponse = bonne_reponse;
        // Lorsque c'est une quesiton pour la mémoire a long terme, le fichier de média est non-null.
        this.media = media;
    }

    public Object Clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public boolean VerifierReponse(String reponse) {
        if (reponse.isEmpty()) {
            return false;
        }
        if (reponse.equals(reponse)) {
            return true;
        }else {
            return false;
        }
    }

    public String getLabel() {
        return Integer.toString(this.id);
    }

    public void setId(String id){
        this.id = Integer.parseInt(id);
    }

    public String getReponse() {
        return this.reponse;
    }

    public void setReponse(String reponse){
        this.reponse = reponse;
    }

    public String getQuestion() {
        return this.questionSentence;
    }

    public void setQuestionSentence(String questionSentence){
        this.questionSentence = questionSentence;
    }

    public String getChoixUn() {
        return this.choixUn;
    }

    public void setChoixUn(String choixUn){
        this.choixUn = choixUn;
    }

    public String getChoixDeux() {
        return this.choixDeux;
    }
    public void setchoixDeux(String choixDeux){
        this.choixDeux = choixDeux;
    }
    public String getChoixTrois() {
        return this.choixTrois;
    }
    public void setChoixTrois() {
        this.choixTrois=choixTrois;
    }
    public String getMedia(){
        return this.media;
    }
    public void setMedia(String media){
        this.media = media;
    }
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", choixUn='" + choixUn + '\'' +
                ", choixDeux='" + choixDeux + '\'' +
                ", choixTrois='" + choixTrois + '\'' +
                ", reponse='" + reponse + '\'' +
                ", questionSentence='" + questionSentence + '\'' +
                ", media='" + media + '\'' +
                '}';
    }
}
