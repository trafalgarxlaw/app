package ca.uqam.info.inf600g.data;

import ca.uqam.info.inf600g.model.Aidant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AidantCollection {
    private static AidantCollection database;

    public static AidantCollection getAccess(){
        if (database == null){
            database = new AidantCollection();
        }
        return database;
    }

    private Map<String, Aidant> aidants;
    public AidantCollection(){
        this.aidants = new HashMap<>();
        initialise();
    }

    private void initialise(){
        Aidant aidant1 = new Aidant(1, "Joseph", "Marc");
        this.aidants.put(aidant1.getLabel(),aidant1);
        Aidant aidant2 = new Aidant(2, "Sebastien", "Mosser");
        this.aidants.put(aidant2.getLabel(), aidant2);
    }

    public Aidant getUnAidant(String id){
        return this.aidants.get(id);
    }

    public Set<Aidant> getAllAidant(){
        return new HashSet<Aidant>(this.aidants.values());
    }
}
