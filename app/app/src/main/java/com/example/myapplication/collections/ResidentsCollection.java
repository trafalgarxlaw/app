package com.example.myapplication.collections;

import com.example.myapplication.models.Resident;

import java.util.ArrayList;

public class ResidentsCollection {
    private ArrayList<Resident> residents;

    public ResidentsCollection(){
        this.residents= new ArrayList<Resident>();
    }

    public void addResident(Resident r){
        residents.add(r);
    }

    public int getSize(){
        return residents.size();
    }

    public Resident getResidentById(int id){
        Resident r = null;

        for (int i = 0; i < residents.size(); i++) {
            if (residents.get(i).getId()==id){

                r=residents.get(i);
            }
        }
        return r;

    }
}
