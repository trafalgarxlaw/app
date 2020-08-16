package com.example.myapplication.models;


public class Aidant {
    private int id;
    private String prenom;
    private String nom;

    public Aidant(int id, String prenom, String nom){
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getLabel(){
        return Integer.toString(id);
    }



}
